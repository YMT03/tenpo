package ie.ramos.tenpo.service.trace;

import ie.ramos.tenpo.dao.Trace;
import ie.ramos.tenpo.repository.TraceRepository;
import ie.ramos.tenpo.service.trace.TraceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraceServiceTest {

    @Mock
    private TraceRepository repository;

    @InjectMocks
    private TraceService service;

    @Test
    public void testSave_OK() {
        Trace trace = buildTrace(randomUUID());

        service.save(trace);

        verify(repository).save(trace);
    }


    @Test
    public void testFindAll_OK() {
        int pageNumber = 0;
        int pageSize = 10;

        List<Trace> traceList = asList(
                buildTrace(randomUUID()),
                buildTrace(randomUUID())
        );

        PageImpl<Trace> transactionsPage = new PageImpl<>(traceList);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        when(repository.findAll(pageable))
                .thenReturn(transactionsPage);

        Page<Trace> retrieved = service.findAll(0, 10);

        assertThat(retrieved).isEqualTo(transactionsPage);

        verify(repository).findAll(pageable);
    }

    private Trace buildTrace(UUID id) {
        return Trace.builder().id(id).build();
    }
}
