package ie.ramos.tenpo.service.trace;

import ie.ramos.tenpo.dao.Trace;
import ie.ramos.tenpo.repository.TraceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TraceService {

    private TraceRepository traceRepository;

    public void save(Trace trace) {
        traceRepository.save(trace);
    }

    public Page<Trace> findAll(int pageNumber, int pageSize) {
        return traceRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

}
