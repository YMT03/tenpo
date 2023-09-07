package ie.ramos.tenpo.exception;


import ie.ramos.tenpo.exception.domain.ApiException;
import ie.ramos.tenpo.exception.domain.BadRequestException;
import ie.ramos.tenpo.exception.domain.NotFoundException;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class ExceptionFactory {

    public NotFoundException buildNotFound(String description, Throwable cause) {
        return new NotFoundException(description, cause);
    }

    public ApiException buildBadRequest(String description, Exception cause) {
        return new BadRequestException(description, cause);
    }

    public ApiException buildInternalServerError(Exception e) {
        return new ApiException(INTERNAL_SERVER_ERROR.name().toLowerCase(), "An error occurred", e.getMessage(), INTERNAL_SERVER_ERROR, e);
    }

}
