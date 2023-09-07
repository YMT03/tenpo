package ie.ramos.tenpo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.ramos.tenpo.exception.ExceptionFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JsonWrapper {
    private final ObjectMapper objectMapper;
    private final ExceptionFactory exceptionFactory;

    public String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw exceptionFactory.buildInternalServerError("json_serialize_error", "An error occurred while mapping Object to JSON String", e.getMessage(), e);
        }
    }

}