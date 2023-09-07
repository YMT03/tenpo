package ie.ramos.tenpo.filter.ratelimit;

import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.github.bucket4j.Bandwidth.classic;
import static io.github.bucket4j.Refill.intervally;
import static java.time.Duration.ofMinutes;

@Configuration
public class BucketConfig {

    @Value("${api.rate-limit.max-rpm}")
    private long maxRPM;

    @Bean
    public Bucket bucket() {
        return Bucket.builder()
                .addLimit(classic(maxRPM, intervally(maxRPM, ofMinutes(1))))
                .build();
    }

}
