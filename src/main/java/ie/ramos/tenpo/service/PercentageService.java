package ie.ramos.tenpo.service;

import ie.ramos.tenpo.rest.percentage.PercentageRestService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static ie.ramos.tenpo.util.Constant.PERCENTAGE_CACHE_NAME;

@Service
@AllArgsConstructor
public class PercentageService {

    private final PercentageRestService percentageRestService;

    @Cacheable(cacheNames = PERCENTAGE_CACHE_NAME)
    public BigDecimal get(BigDecimal numberOne, BigDecimal numberTwo) {
        return percentageRestService.get(numberOne, numberTwo);
    }

}
