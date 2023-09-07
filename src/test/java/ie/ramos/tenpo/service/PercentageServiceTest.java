package ie.ramos.tenpo.service;


import ie.ramos.tenpo.rest.percentage.PercentageRestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PercentageServiceTest {

    @Mock
    private PercentageRestService percentageRestService;

    @InjectMocks
    private PercentageService service;

    @Test
    public void testGet_OK() {
        var numberOne = new BigDecimal("123.33");
        var numberTwo = new BigDecimal("5.5555");
        var expected = new BigDecimal("1.1");

        when(percentageRestService.get(numberOne, numberTwo))
                .thenReturn(expected);

        var retrieved = service.get(numberOne, numberTwo);

        assertThat(retrieved).isEqualTo(expected);

        verify(percentageRestService).get(numberOne, numberTwo);
    }
}
