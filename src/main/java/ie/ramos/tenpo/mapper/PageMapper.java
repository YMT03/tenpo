package ie.ramos.tenpo.mapper;


import ie.ramos.tenpo.dto.out.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {

    public <T> PageDTO<T> mapToPageDTO(Page<T> page) {
        return new PageDTO<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalPages(), page.getTotalElements());
    }
}
