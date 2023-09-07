package ie.ramos.tenpo.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.ramos.tenpo.exception.ExceptionFactory;
import ie.ramos.tenpo.exception.domain.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ExtendWith(MockitoExtension.class)
public class JsonWrapperTest {

    @Mock
    private ExceptionFactory exceptionFactory;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private JsonWrapper jsonWrapper;


    @Test
    public void testToJsonString_OK() throws JsonProcessingException {
        var object = new Object();
        var expected = "{jsonString}";

        when(objectMapper.writeValueAsString(object))
                .thenReturn(expected);

        var retrieved = jsonWrapper.toJsonString(object);

        assertThat(retrieved).isEqualTo(expected);

        verify(objectMapper).writeValueAsString(object);
    }


    @Test
    public void testToJsonString_ObjectMapperThrowsJsonProcessingException_ThrowsJsonSerializeApiException() throws JsonProcessingException {
        var object = new Object();
        var errorId = "json_serialize_error";
        var title = "An error occurred while mapping Object to JSON String";
        var jsonProcessingMessage = "Something bad happened yo";

        var jsonProcessingException = new JsonProcessingException(jsonProcessingMessage){};

        when(objectMapper.writeValueAsString(object))
                .thenThrow(jsonProcessingException);

        when(exceptionFactory.buildInternalServerError(errorId, title, jsonProcessingMessage, jsonProcessingException))
                .thenReturn(buildApiException(errorId, title, jsonProcessingMessage, jsonProcessingException));



        var retrieved = assertThrows(ApiException.class, () -> jsonWrapper.toJsonString(object));

        assertThat(retrieved)
                .isExactlyInstanceOf(ApiException.class)
                .hasFieldOrPropertyWithValue("id", errorId)
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("description", jsonProcessingMessage)
                .hasMessage(jsonProcessingMessage);


        verify(objectMapper).writeValueAsString(object);
        verify(exceptionFactory).buildInternalServerError(errorId, title, jsonProcessingMessage, jsonProcessingException);
    }

    private ApiException buildApiException(String errorId, String title, String message, JsonProcessingException cause) {
        return new ApiException(errorId, title, message, INTERNAL_SERVER_ERROR, cause);
    }


}
