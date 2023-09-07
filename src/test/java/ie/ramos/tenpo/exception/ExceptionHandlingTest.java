package ie.ramos.tenpo.exception;

import ie.ramos.tenpo.dto.out.ErrorDTO;
import ie.ramos.tenpo.exception.domain.ApiException;
import ie.ramos.tenpo.mapper.ErrorMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.EMPTY;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlingTest {

    @InjectMocks
    private ExceptionHandling exceptionHandling;
    @Mock
    private ExceptionFactory exceptionFactory;
    @Mock
    private ErrorMapper errorMapper;


    @Test
    public void testHandle_NoHandlerFoundException_ReturnsNoMappingFoundError() {
        var exception = new NoHandlerFoundException("httpMethod", "requestURL", EMPTY);
        var apiException = buildApiException(NOT_FOUND, exception);
        var errorDTO = buildErrorDTO("no_mapping_found");

        when(exceptionFactory.buildNoMappingFound(exception))
                .thenReturn(apiException);

        when(errorMapper.mapToDTO(apiException))
                .thenReturn(errorDTO);

        var response = exceptionHandling.handle(exception);

        var expected = ResponseEntity.status(apiException.getHttpStatus()).body(errorDTO);

        assertThat(response).isEqualTo(expected);

        verify(exceptionFactory).buildNoMappingFound(exception);
        verify(errorMapper).mapToDTO(apiException);

    }

    @Test
    public void testHandle_ApiException_Returns_ApiError() {
        var apiException = buildApiException(GATEWAY_TIMEOUT, new RuntimeException());
        var errorDTO = buildErrorDTO("some_error_id");

        when(errorMapper.mapToDTO(apiException))
                .thenReturn(errorDTO);

        var response = exceptionHandling.handle(apiException);

        var expected = ResponseEntity.status(apiException.getHttpStatus()).body(errorDTO);

        assertThat(response).isEqualTo(expected);

        verify(errorMapper).mapToDTO(apiException);
    }

    @Test
    public void testHandle_Exception_CauseIsApiException_Returns_ApiError() {
        var apiException = buildApiException(NOT_FOUND, new RuntimeException());
        var errorDTO = buildErrorDTO("some_error_id");

        when(errorMapper.mapToDTO(apiException))
                .thenReturn(errorDTO);

        var response = exceptionHandling.handle(new RuntimeException(apiException));

        var expected = ResponseEntity.status(apiException.getHttpStatus()).body(errorDTO);

        assertThat(response).isEqualTo(expected);

        verify(errorMapper).mapToDTO(apiException);
    }

    @Test
    public void testHandle_Exception_CauseIsApiNotException_Returns_InternalServerApiError() {
        var exception = new RuntimeException();
        var apiException = buildApiException(INTERNAL_SERVER_ERROR, exception);
        var errorDTO = buildErrorDTO("internal_server_error");

        when(errorMapper.mapToDTO(apiException))
                .thenReturn(errorDTO);

        when(exceptionFactory.buildInternalServerError(exception))
                .thenReturn(apiException);


        var response = exceptionHandling.handle(exception);

        var expected = ResponseEntity.status(apiException.getHttpStatus()).body(errorDTO);

        assertThat(response).isEqualTo(expected);

        verify(errorMapper).mapToDTO(apiException);
        verify(exceptionFactory).buildInternalServerError(exception);
    }


    @Test
    public void testHandle_HttpMessageNotReadableException_ReturnsBadRequestError() {
        var exception = new HttpMessageNotReadableException("not redeable");
        var apiException = buildApiException(BAD_REQUEST, exception);
        var errorDTO = buildErrorDTO("bad_request");

        when(errorMapper.mapToDTO(apiException))
                .thenReturn(errorDTO);

        when(exceptionFactory.buildBadRequest(exception.getMessage(), exception))
                .thenReturn(apiException);

        var response = exceptionHandling.handle(exception);

        var expected = ResponseEntity.status(apiException.getHttpStatus()).body(errorDTO);

        assertThat(response).isEqualTo(expected);

        verify(errorMapper).mapToDTO(apiException);
        verify(exceptionFactory).buildBadRequest(exception.getMessage(), exception);
    }

    @Test
    public void testHandle_BindException_ReturnsBadRequestError() {
        var bindException = buildMockBindException();
        var apiException = buildApiException(BAD_REQUEST, bindException);
        var errorDTO = buildErrorDTO("bad_request");
        String expectedMessage = "Error with Object object. Field field1: Error message 1, Field field2: Error message 2, Field field2: Error message 3";

        when(errorMapper.mapToDTO(apiException))
                .thenReturn(errorDTO);

        when(exceptionFactory.buildBadRequest(expectedMessage, bindException))
                .thenReturn(apiException);

        var response = exceptionHandling.handle(bindException);

        var expected = ResponseEntity.status(apiException.getHttpStatus()).body(errorDTO);

        assertThat(response).isEqualTo(expected);

        verify(errorMapper).mapToDTO(apiException);
        verify(exceptionFactory).buildBadRequest(expectedMessage, bindException);
    }

    private static BindException buildMockBindException() {
        var bindException = new BindException(new Object(), "object");
        bindException.addError(buildFieldError("field1", 1));
        bindException.addError(buildFieldError("field2", 2));
        bindException.addError(buildFieldError("field2", 3));
        return bindException;
    }

    private static FieldError buildFieldError(String fieldName, int number) {
        return new FieldError("object", fieldName, String.format("Error message %d", number));
    }


    private ErrorDTO buildErrorDTO(String id) {
        return ErrorDTO.builder()
                .id(id)
                .build();
    }


    private ApiException buildApiException(HttpStatus status, Exception e) {
        return new ApiException(status, "", e);
    }


}