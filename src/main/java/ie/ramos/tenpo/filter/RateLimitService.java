package ie.ramos.tenpo.filter;

import ie.ramos.tenpo.exception.domain.TooManyRequestException;
import io.github.bucket4j.Bucket;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class RateLimitService {

    private final Bucket bucket;

    public void check() {
        if (!bucket.tryConsume(1))
            throw new TooManyRequestException();
    }

}
