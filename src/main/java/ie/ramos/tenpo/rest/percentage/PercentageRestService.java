package ie.ramos.tenpo.rest.percentage;

import org.springframework.retry.annotation.Retryable;

import java.math.BigDecimal;

public interface PercentageRestService {
    @Retryable
    BigDecimal get(BigDecimal numberOne, BigDecimal numberTwo);

}
