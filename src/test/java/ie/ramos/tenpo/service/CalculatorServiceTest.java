package ie.ramos.tenpo.service;

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
public class CalculatorServiceTest {

    @Mock
    private PercentageService percentageService;

    @InjectMocks
    private CalculatorService calculatorService;


    @Test
    public void testSumWithPercentage_OK() {
        assertResult("10", "10", "10", "22.00");
    }

    @Test
    public void testSumWithPercentage_WithDecimals_OK() {
        assertResult("9.5555", "3.55", "13.5", "14.87");
    }

    @Test
    public void testSumWithPercentage_WithZeroPercentage_OK() {
        assertResult("20", "10", "0", "30.00");
    }

    @Test
    public void testSumWithPercentage_WithNegativeNum_OK() {
        assertResult("-20", "-15.5", "32", "-46.86");
    }

    @Test
    public void testSumWithPercentage_RoundsUp_OK() {
        assertResult("10.5", "10", "15", "23.58");
    }

    @Test
    public void testSumWithPercentage_WithNegativePercentage_OK() {
        assertResult("100.50", "150.5", "-22", "195.78");
    }

    private void assertResult(String first, String second, String retrievedPercentage, String expected) {
        BigDecimal firstDecimal = new BigDecimal(first);
        BigDecimal secondDecimal = new BigDecimal(second);

        when(percentageService.get(firstDecimal, secondDecimal))
                .thenReturn(new BigDecimal(retrievedPercentage));

        assertThat(calculatorService.getSumWithPercentage(firstDecimal, secondDecimal))
                .isEqualTo(new BigDecimal(expected));

        verify(percentageService).get(firstDecimal, secondDecimal);
    }


}
