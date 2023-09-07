package ie.ramos.tenpo.rest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class RestService {

    private final RestTemplate restTemplate;

    public <T> T get(String url, Class<T> clazz) {
        return restTemplate.getForObject(url, clazz);
    }

}
