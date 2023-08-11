package guru.springframework.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateExamples
{

    public static final String API_ROOT = "https://api.predic8.de/shop/v2";

    @Test
    public void getVendors() throws Exception {
        String apiUrl = API_ROOT + "/vendors/";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void getCustomers() throws Exception {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void createVendor() throws Exception {
        String apiUrl = API_ROOT + "/vendors/";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Joe");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void updateVendor() throws Exception {

        //create customer to update
        String apiUrl = API_ROOT + "/vendors/";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Micheal");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String vendorUrl = jsonNode.get("self_link").textValue();

        String id = vendorUrl.split("/")[4];

        System.out.println("Created vendor id: " + id);

        postMap.put("name", "Micheal 2");

        restTemplate.put(apiUrl + id, postMap);

        JsonNode updatedNode = restTemplate.getForObject(apiUrl + id, JsonNode.class);

        System.out.println(updatedNode.toString());

    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteProduct() throws Exception {

        //create customer to update
        String apiUrl = API_ROOT + "/products/";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Banana");
        postMap.put("price", "21.31");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("self_link").textValue();

        String id = customerUrl.split("/")[4];

        System.out.println("Created product id: " + id);

        restTemplate.delete(apiUrl + id); //expects 200 status

        System.out.println("Product deleted");

        //should go boom on 404
        restTemplate.getForObject(apiUrl + id, JsonNode.class);

    }


}
