package ie.ramos.tenpo.rest.percentage;

import ie.ramos.tenpo.rest.RestService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static ie.ramos.tenpo.util.Constant.PROD_PROFILE;

@Service
@AllArgsConstructor
@Profile(PROD_PROFILE)
public class PercentageRestServiceImp implements PercentageRestService {

    private final String uri = "http://some-domain/percentages/numberOne=%.2f&numberTwo=%.2f";
    private final RestService restService;

    @Override
    public BigDecimal get(BigDecimal numberOne, BigDecimal numberTwo) {
        return restService.get(String.format(uri, numberOne, numberTwo), BigDecimal.class);
    }

}
