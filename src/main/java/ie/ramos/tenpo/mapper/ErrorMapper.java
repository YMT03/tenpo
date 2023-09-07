package ie.ramos.tenpo.mapper;


import ie.ramos.tenpo.dto.out.ErrorDTO;
import ie.ramos.tenpo.exception.domain.ApiException;
import org.springframework.stereotype.Component;

@Component
public class ErrorMapper {

    public ErrorDTO mapToDTO(ApiException apiException) {
        return ErrorDTO.builder()
                .id(apiException.getId())
                .title(apiException.getTitle())
                .description(apiException.getDescription())
                .httpStatus(apiException.getHttpStatus().value())
                .date(apiException.getDate())
                .build();
    }

}
