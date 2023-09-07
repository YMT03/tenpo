package ie.ramos.tenpo.exception.domain;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends ApiException {

    public NotFoundException(String description, Throwable cause) {
        super(NOT_FOUND, description, cause);
    }

}
