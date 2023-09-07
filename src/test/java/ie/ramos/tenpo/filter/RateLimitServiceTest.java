package ie.ramos.tenpo.filter;


import ie.ramos.tenpo.exception.domain.TooManyRequestException;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateLimitServiceTest {

    @Mock
    private Bucket bucket;

    @InjectMocks
    private RateLimitService service;


    @Test
    public void testCheck_OK() {
        when(bucket.tryConsume(1))
                .thenReturn(true);

        service.check();

        verify(bucket).tryConsume(1);
    }

    @Test
    public void testCheck_BucketIsFull_ThrowsTooManyRequestException() {

        when(bucket.tryConsume(1))
                .thenReturn(false);

        Exception exception = assertThrows(TooManyRequestException.class, () -> service.check());

        assertThat(exception)
                .isExactlyInstanceOf(TooManyRequestException.class)
                .hasMessage("You have exceeded the rate limit. Try again later.");

        verify(bucket).tryConsume(1);
    }


}
