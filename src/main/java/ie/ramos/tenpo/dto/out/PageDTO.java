package ie.ramos.tenpo.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    private List<T> objects;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalElements;
}
