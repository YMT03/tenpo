package ie.ramos.tenpo.rest;

import ie.ramos.tenpo.rest.percentage.PercentageRestServiceMock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class PercentageRestServiceMockTest {

    private final PercentageRestServiceMock restServiceMock = new PercentageRestServiceMock();

    @Test
    public void testGet_OK() {
        assertGet("1", "2", "3.0");
        assertGet("-1", "2", "1.0");
        assertGet("1.5", "2.1111", "3.6");
        assertGet("1.55", "2", "3.5");
    }

    private void assertGet(String numberOne, String numberTwo, String expected) {
        BigDecimal numberOneDecimal = new BigDecimal(numberOne);
        BigDecimal numberTwoDecimal = new BigDecimal(numberTwo);

        BigDecimal expectedDecimal = new BigDecimal(expected);

        BigDecimal retrieved = restServiceMock.get(numberOneDecimal, numberTwoDecimal);

        assertThat(retrieved).isEqualTo(expectedDecimal);
    }

}
