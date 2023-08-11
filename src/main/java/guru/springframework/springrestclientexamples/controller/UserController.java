package guru.springframework.springrestclientexamples.controller;

import guru.springframework.springrestclientexamples.services.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController
{
    private final ApiService apiService;

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/users")
    public String formPost(Model model, ServerWebExchange serverWebExchange) {

        /*MultiValueMap<String, String> map = serverWebExchange.getFormData().block();

        Integer limit = Integer.valueOf(map.get("limit").get(0));

        log.debug("Received Limit value: " + limit);
        //default if null or zero
        if(limit == null || limit == 0){
            log.debug("Setting limit to default of 10");
            limit = 10;
        }

        model.addAttribute("users", apiService.getUsers(limit));
*/
        model.addAttribute("users", apiService.getUsers(serverWebExchange
            .getFormData()
            .map(data -> Integer.valueOf(data.getFirst("limit")))));
        return "userlist";
    }
}
