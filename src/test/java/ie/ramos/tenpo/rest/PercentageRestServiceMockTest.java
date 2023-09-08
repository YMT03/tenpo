package ie.ramos.tenpo.rest;

import ie.ramos.tenpo.exception.domain.TooManyRequestException;
import ie.ramos.tenpo.rest.percentage.PercentageRestServiceMock;
import ie.ramos.tenpo.util.RandomGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PercentageRestServiceMockTest {

    @InjectMocks
    private PercentageRestServiceMock restServiceMock;

    @Mock
    private RandomGenerator randomGenerator;

    @Test
    public void testGet_OK() {
        assertGetOK("1", "2", "3.0");
        assertGetOK("-1", "2", "1.0");
        assertGetOK("1.5", "2.1111", "3.6");
        assertGetOK("1.55", "2", "3.5");
        verify(randomGenerator, times(4)).getRandomBoolean(anyDouble());
    }

    @Test
    public void testGet_ShouldFailReturnsTrue_ThrowsHttpServerErrorException() {
        when(randomGenerator.getRandomBoolean(anyDouble()))
                .thenReturn(true);

        Exception e = assertThrows(HttpServerErrorException.class, () -> restServiceMock.get(new BigDecimal("1"), new BigDecimal("2")));

        assertThat(e).isExactlyInstanceOf(ServiceUnavailable.class);

        verify(randomGenerator).getRandomBoolean(anyDouble());
    }

    private void assertGetOK(String numberOne, String numberTwo, String expected) {
        BigDecimal numberOneDecimal = new BigDecimal(numberOne);
        BigDecimal numberTwoDecimal = new BigDecimal(numberTwo);

        BigDecimal expectedDecimal = new BigDecimal(expected);

        BigDecimal retrieved = restServiceMock.get(numberOneDecimal, numberTwoDecimal);

        assertThat(retrieved).isEqualTo(expectedDecimal);
    }

}
