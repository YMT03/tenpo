package ie.ramos.tenpo.endpoint;


import ie.ramos.tenpo.dto.in.CalculatorRequestDTO;
import ie.ramos.tenpo.dto.out.CalculatorResponseDTO;
import ie.ramos.tenpo.service.CalculatorService;
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
public class CalculatorEndpointTest {

    @Mock
    private CalculatorService calculatorService;

    @InjectMocks
    private CalculatorEndpoint calculatorEndpoint;

    @Test
    public void testGet_OK() {
        var numberOne = new BigDecimal("123.433");
        var numberTwo = new BigDecimal("34.2");
        var result = new BigDecimal("303.123");

        when(calculatorService.getSumWithPercentage(numberOne, numberTwo))
                .thenReturn(result);


        var retrieved = calculatorEndpoint.get(buildRequestDTO(numberOne, numberTwo));
        var expected = buildResponseDTO(result);

        assertThat(retrieved).isEqualTo(expected);

        verify(calculatorService).getSumWithPercentage(numberOne, numberTwo);
    }

    private CalculatorResponseDTO buildResponseDTO(BigDecimal result) {
        return CalculatorResponseDTO.builder().result(result).build();
    }

    private static CalculatorRequestDTO buildRequestDTO(BigDecimal numberOne, BigDecimal numberTwo) {
        return CalculatorRequestDTO.builder().numberOne(numberOne).numberTwo(numberTwo).build();
    }
}
