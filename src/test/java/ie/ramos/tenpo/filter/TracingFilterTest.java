package ie.ramos.tenpo.filter;


import ie.ramos.tenpo.service.trace.AsyncTracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

import static java.util.Arrays.*;
import static java.util.Collections.enumeration;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TracingFilterTest {

    @InjectMocks
    private TracingFilter filter;
    @Mock
    private AsyncTracer asyncTracer;
    @Mock
    private ContentCachingRequestWrapper request;
    @Mock
    private ContentCachingResponseWrapper response;
    @Mock
    private FilterChain filterChain;


    @Test
    public void testDoFilterInternal() throws ServletException, IOException {
        when(request.getRequestURI())
                .thenReturn("/api/uri");
        when(request.getMethod())
                .thenReturn("GET");
        when(request.getQueryString())
                .thenReturn("query=string&quero=strong");
        when(request.getHeaderNames())
                .thenReturn(enumeration(asList("header1", "header2")));
        when(request.getHeader("header1"))
                .thenReturn("value1");
        when(request.getHeader("header2"))
                .thenReturn("value2");
        when(request.getContentAsByteArray())
                .thenReturn(getRequestContentMock());
        when(response.getContentAsByteArray())
                .thenReturn(getResponseContentMock());
        when(response.getStatus())
                .thenReturn(503);

        filter.doFilterInternal(request, response, filterChain);

        verify(asyncTracer).trace(
                "/api/uri",
                "GET",
                "query=string&quero=strong",
                "header1:value1;header2:value2;",
                "{ \"name\": \"value\" }",
                "{ \"id\":\"bad_request\", \"title\":\"Bad Request\",\"description\":\"bad request\",\"http_status\":400,\"date\":\"2023-09-07T15:57:08.12366-03:00\" }",
                503
        );

        verify(response).copyBodyToResponse();
        verify(request).getRequestURI();
        verify(request).getMethod();
        verify(request).getQueryString();
        verify(request).getHeaderNames();
        verify(request).getHeader("header1");
        verify(request).getHeader("header2");
        verify(request).getContentAsByteArray();
        verify(response).getContentAsByteArray();
        verify(response).getStatus();
    }

    private byte[] getRequestContentMock() {
        return "{ \n  \t    \"name\":  \"value\"                }".getBytes();
    }

    private byte[] getResponseContentMock() {
        return "{  \n  \r  \t       \"id\":\"bad_request\",               \"title\":\"Bad Request\",\"description\":\"bad request\",\"http_status\":400,\"date\":\"2023-09-07T15:57:08.12366-03:00\"      }".getBytes();
    }
}
