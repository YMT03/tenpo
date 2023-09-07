package ie.ramos.tenpo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;

@Service
@AllArgsConstructor
public class CalculatorService {

    private PercentageService percentageService;
    private final Integer TWO = 2;

    public BigDecimal getSumWithPercentage(BigDecimal numberOne, BigDecimal numberTwo) {
        return numberOne.add(numberTwo)
                .multiply(getMultiplicand(numberOne, numberTwo))
                .setScale(TWO, HALF_UP);
    }

    private BigDecimal getMultiplicand(BigDecimal numberOne, BigDecimal numberTwo) {
        return percentageService.get(numberOne, numberTwo).movePointLeft(TWO).add(ONE);
    }

}
