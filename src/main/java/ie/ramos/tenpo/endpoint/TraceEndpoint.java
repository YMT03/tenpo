package ie.ramos.tenpo.endpoint;


import ie.ramos.tenpo.dao.Trace;
import ie.ramos.tenpo.dto.out.PageDTO;
import ie.ramos.tenpo.mapper.PageMapper;
import ie.ramos.tenpo.service.trace.TraceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class TraceEndpoint {

    private final TraceService traceService;
    private final PageMapper pageMapper;

    @GetMapping("/internal/traces")
    public PageDTO<Trace> get(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {
        return pageMapper.mapToPageDTO(traceService.findAll(page, size));
    }
}
