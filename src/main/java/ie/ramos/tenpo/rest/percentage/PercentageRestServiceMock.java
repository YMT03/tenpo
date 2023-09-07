package ie.ramos.tenpo.rest.percentage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ie.ramos.tenpo.config.AppConfig.DEV_PROFILE;


@Service
@Profile(DEV_PROFILE)
public class PercentageRestServiceMock implements PercentageRestService {

    @Override
    public BigDecimal get(BigDecimal numberOne, BigDecimal numberTwo) {
        return numberOne.add(numberTwo).setScale(1, RoundingMode.HALF_DOWN);
    }
}
