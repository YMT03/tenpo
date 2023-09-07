package ie.ramos.tenpo.service;

import ie.ramos.tenpo.rest.percentage.PercentageRestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PercentageService {

    private final PercentageRestService percentageRestService;

    public BigDecimal get(BigDecimal numberOne, BigDecimal numberTwo) {
        return percentageRestService.get(numberOne, numberTwo);
    }

}
