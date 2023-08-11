package guru.springframework.springrestclientexamples.services;

import guru.springframework.api.domain.User;
import guru.springframework.api.domain.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService
{
    private final RestTemplate restTemplate;

    @Value(value = "${api.url}")
    private String apiUrl;

    //Api doesn't let free trial with mock server you can only get oen response
    @Override
    public List<User> getUsers(Integer limit) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
            .fromUriString(apiUrl)
            .queryParam("limit", limit);
        UserData userData = restTemplate.getForObject(uriBuilder.toUriString(), UserData.class);
        User user = userData.getData().get(0);
        for (int i = 1; i<limit;i++){
            userData.getData().add(user);
        }
        return userData.getData();
    }

    //Api only returns one user no matter what the limit
    @Override
    public Flux<User> getUsers(Mono<Integer> limit) {
        return WebClient
            .create(apiUrl)
            .get()
            .uri(uriBuilder -> uriBuilder.queryParam("limit",limit.block()).build())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .flatMap(resp -> resp.bodyToMono(UserData.class))
            .flatMapIterable(UserData::getData);
    }
}
