package ie.ramos.tenpo.exception;


import ie.ramos.tenpo.exception.domain.ApiException;
import ie.ramos.tenpo.exception.domain.BadRequestException;
import ie.ramos.tenpo.exception.domain.NotFoundException;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class ExceptionFactory {

    public NotFoundException buildNotFound(String description, Throwable cause) {
        return new NotFoundException(description, cause);
    }

    public ApiException buildNoMappingFound(Exception e) {
        return new ApiException("no_mapping_found", "No mapping found", e.getMessage(), NOT_FOUND, e);
    }

    public ApiException buildBadRequest(String description, Exception cause) {
        return new BadRequestException(description, cause);
    }

    public ApiException buildInternalServerError(String id, String title, String description, Exception e) {
        return new ApiException(id, title, description, INTERNAL_SERVER_ERROR, e);
    }

    public ApiException buildInternalServerError(Exception e) {
        return new ApiException(INTERNAL_SERVER_ERROR.name().toLowerCase(), "An error occurred", e.getMessage(), INTERNAL_SERVER_ERROR, e);
    }

}
