package ie.ramos.tenpo.exception.domain;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

public class TooManyRequestException extends ApiException {

    public TooManyRequestException() {
        super(TOO_MANY_REQUESTS, "You have exceeded the rate limit. Try again later.", null);
    }
}
