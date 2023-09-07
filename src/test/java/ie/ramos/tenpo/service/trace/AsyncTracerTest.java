package ie.ramos.tenpo.service.trace;


import ie.ramos.tenpo.dao.Trace;
import ie.ramos.tenpo.service.trace.AsyncTracer;
import ie.ramos.tenpo.service.trace.TraceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AsyncTracerTest {

    @Mock
    private TraceService traceService;

    @InjectMocks
    private AsyncTracer asyncTracer;

    @Test
    public void testSave_OK() {
        var uri = "/test";
        var method = "GET";
        var headers = "some-header-values";
        var request = "{some-request-json}";
        var response = "{some-response-json}";
        int httpStatus = HttpStatus.OK.value();

        var expectedTrace = buildTrace(uri, method, headers, request, response, httpStatus);

        asyncTracer.trace(uri, method, headers, request, response, httpStatus);

        verify(traceService).save(expectedTrace);
    }


    private Trace buildTrace(String uri, String method, String headers, String request, String response, int httpStatus) {
        return Trace.builder()
                .uri(uri)
                .method(method)
                .headers(headers)
                .request(request)
                .response(response)
                .httpStatus(httpStatus)
                .build();
    }

}
