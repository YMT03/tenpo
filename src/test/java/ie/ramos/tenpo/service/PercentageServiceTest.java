package ie.ramos.tenpo.service;


import ie.ramos.tenpo.rest.PercentageRestService;
import org.assertj.core.api.Assertions;
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
        BigDecimal numberOne = new BigDecimal("123.33");
        BigDecimal numberTwo = new BigDecimal("5.5555");
        BigDecimal expected = new BigDecimal("1.1");

        when(percentageRestService.get(numberOne, numberTwo))
                .thenReturn(expected);

        BigDecimal retrieved = service.get(numberOne, numberTwo);

        assertThat(retrieved).isEqualTo(expected);

        verify(percentageRestService).get(numberOne, numberTwo);
    }
}
