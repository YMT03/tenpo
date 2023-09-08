package ie.ramos.tenpo.rest.percentage;

import ie.ramos.tenpo.util.RandomGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ie.ramos.tenpo.util.Constant.DEV_PROFILE;
import static org.springframework.http.HttpStatusCode.valueOf;


@Service
@Profile(DEV_PROFILE)
@Slf4j
@RequiredArgsConstructor
public class PercentageRestServiceMock implements PercentageRestService {

    @Value("${api.rest.percentage-mock.fail-probability}")
    private double failProbability;
    private final RandomGenerator randomGenerator;

    @Override
    @SneakyThrows
    public BigDecimal get(BigDecimal numberOne, BigDecimal numberTwo) {
        log.info(String.format("Performing fake API Call with args number_one: %s, number_two: %s", numberOne, numberTwo));
        if (shouldFail()) {
            throw buildServiceUnavailable();
        }
        return numberOne.add(numberTwo).setScale(1, RoundingMode.HALF_DOWN);
    }

    private boolean shouldFail() {
        return randomGenerator.getRandomBoolean(failProbability);
    }

    private HttpServerErrorException buildServiceUnavailable() {
        log.info("Fake API Call failed");
        return HttpServerErrorException.create(
                valueOf(503),
                "Service Unavailable",
                HttpHeaders.EMPTY,
                "{some-external-body}".getBytes(),
                null);
    }
}
