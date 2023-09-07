package ie.ramos.tenpo.exception.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

import static java.time.OffsetDateTime.now;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiException extends RuntimeException {
    private final String id;
    private final String title;
    private final String description;
    private final HttpStatus httpStatus;
    private final OffsetDateTime date = now();

    public ApiException(String id, String title, String description, HttpStatus httpStatus, Throwable cause) {
        super(description, cause);
        this.id = id;
        this.title = title;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpStatus httpStatus, String description, Throwable cause) {
        super(description, cause);
        this.id = httpStatus.name().toLowerCase();
        this.title = httpStatus.getReasonPhrase();
        this.description = description;
        this.httpStatus = httpStatus;
    }

}
