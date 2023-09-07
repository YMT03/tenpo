package ie.ramos.tenpo.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestServiceTest {

    @InjectMocks
    private RestService restService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testGet_OK() {
        var url = "url";
        var clazz = String.class;
        String expected = "object";

        when(restTemplate.getForObject(url, clazz))
                .thenReturn(expected);

        Object retrieved = restService.get(url, clazz);

        assertThat(retrieved).isEqualTo(expected);

        verify(restTemplate).getForObject(url, clazz);
    }


}
