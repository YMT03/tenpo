package ie.ramos.tenpo.rest;

import ie.ramos.tenpo.rest.percentage.PercentageRestServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PercentageRestServiceImpTest {

    @InjectMocks
    private PercentageRestServiceImp restServiceImp;
    @Mock
    private RestService restService;

    @Test
    public void testGet_OK() {
        BigDecimal numberOne = new BigDecimal("1.235");
        BigDecimal numberTwo = new BigDecimal("2.354");
        String expectedUri = "http://some-domain/percentages/numberOne=1,24&numberTwo=2,35";
        BigDecimal expected = new BigDecimal("5.3");

        when(restService.get(expectedUri, BigDecimal.class))
                .thenReturn(expected);

        BigDecimal retrieved = restServiceImp.get(numberOne, numberTwo);

        assertThat(retrieved).isEqualTo(expected);

        verify(restService).get(expectedUri, BigDecimal.class);
    }

}
