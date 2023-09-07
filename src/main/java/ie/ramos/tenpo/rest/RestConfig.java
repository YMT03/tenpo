package ie.ramos.tenpo.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestConfig {

    @Value("${api.rest.connection-timeout}")
    private Duration connectionTimeout;
    @Value("${api.rest.read-timeout}")
    private Duration readTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setReadTimeout(readTimeout)
                .setConnectTimeout(connectionTimeout)
                .build();
    }
}
