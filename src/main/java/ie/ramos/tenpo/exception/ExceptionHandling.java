package ie.ramos.tenpo.exception;


import ie.ramos.tenpo.dto.out.ErrorDTO;
import ie.ramos.tenpo.exception.domain.ApiException;
import ie.ramos.tenpo.mapper.ErrorMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatusCode.valueOf;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandling {

    private final ExceptionFactory exceptionFactory;
    private final ErrorMapper errorMapper;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDTO> handle(ApiException e) {
        return handleResponse(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handle(Exception e) {
        return handleResponse(getApiException(e));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handle(HttpMessageNotReadableException e) {
        return handleResponse(exceptionFactory.buildBadRequest(e.getMessage(), e));
    }

    private ResponseEntity<ErrorDTO> handleResponse(ApiException apiException) {
        return new ResponseEntity<>(
                errorMapper.mapToDTO(apiException),
                valueOf(apiException.getHttpStatus().value())
        );
    }

    private ApiException getApiException(Exception e) {
        if (e instanceof ApiException ae)
            return ae;
        if (e.getCause() instanceof ApiException ae)
            return ae;
        return exceptionFactory.buildInternalServerError(e);
    }

}
