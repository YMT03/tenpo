package ie.ramos.tenpo.rest;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PercentageRestService {

    public BigDecimal get(BigDecimal numberOne, BigDecimal numberTwo) {
        return numberOne.add(numberTwo).setScale(1, RoundingMode.HALF_DOWN);
    }
}
