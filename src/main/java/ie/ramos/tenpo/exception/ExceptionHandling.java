package ie.ramos.tenpo.exception;


import ie.ramos.tenpo.dto.out.ErrorDTO;
import ie.ramos.tenpo.exception.domain.ApiException;
import ie.ramos.tenpo.mapper.ErrorMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatusCode.valueOf;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandling {

    private final ExceptionFactory exceptionFactory;
    private final ErrorMapper errorMapper;

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDTO> handle(NoHandlerFoundException e) {
        return handleResponse(exceptionFactory.buildNoMappingFound(e));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDTO> handle(ApiException e) {
        return handleResponse(e);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorDTO> handle(BindException e) {
        String description = String.format("Error with Object %s. %s", e.getObjectName(), getCollapsedBindingErrors(e));
        return handleResponse(exceptionFactory.buildBadRequest(description, e));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handle(HttpMessageNotReadableException e) {
        return handleResponse(exceptionFactory.buildBadRequest(e.getMessage(), e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handle(Exception e) {
        return handleResponse(getApiException(e));
    }

    private static String getCollapsedBindingErrors(BindException e) {
        return e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> String.format("Field %s: %s", error.getField(), error.getDefaultMessage()))
                .collect(joining(", "));
    }

    private ResponseEntity<ErrorDTO> handleResponse(ApiException apiException) {
        return new ResponseEntity<>(errorMapper.mapToDTO(apiException), valueOf(apiException.getHttpStatus().value()));
    }

    private ApiException getApiException(Exception e) {
        if (e instanceof ApiException ae)
            return ae;
        if (e.getCause() instanceof ApiException ae)
            return ae;
        return exceptionFactory.buildInternalServerError(e);
    }

}
