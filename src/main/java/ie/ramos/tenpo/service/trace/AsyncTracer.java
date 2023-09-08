package ie.ramos.tenpo.service.trace;

import ie.ramos.tenpo.dao.Trace;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AsyncTracer {

    private final TraceService traceService;

    @Async
    public void trace(String uri, String method, String queryString, String headers, String request, String response, int httpStatus) {
        traceService.save(buildTrace(uri, method, queryString, headers, request, response, httpStatus));
    }

    private Trace buildTrace(String uri, String method, String queryString, String headers, String request, String response, int httpStatus) {
        return Trace.builder()
                .uri(uri)
                .method(method)
                .queryString(queryString)
                .headers(headers)
                .request(request)
                .response(response)
                .httpStatus(httpStatus)
                .build();
    }
}
