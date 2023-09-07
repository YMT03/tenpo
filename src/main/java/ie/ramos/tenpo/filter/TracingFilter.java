package ie.ramos.tenpo.filter;

import ie.ramos.tenpo.service.trace.AsyncTracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;

import static java.util.Arrays.copyOf;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

@Component
@Order(2)
@AllArgsConstructor
public class TracingFilter extends OncePerRequestFilter {

    private final AsyncTracer asyncTracer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var responseWrapper = response instanceof ContentCachingResponseWrapper rw ? rw : new ContentCachingResponseWrapper(response);
        var requestWrapper = request instanceof ContentCachingRequestWrapper rq ? rq : new ContentCachingRequestWrapper(request);

        filterChain.doFilter(requestWrapper, responseWrapper);

        trace(requestWrapper, responseWrapper);

        responseWrapper.copyBodyToResponse();
    }

    private void trace(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) {
        asyncTracer.trace(
                requestWrapper.getRequestURI(),
                requestWrapper.getMethod(),
                getHeaders(requestWrapper),
                getRequestString(requestWrapper),
                getResponseString(responseWrapper),
                responseWrapper.getStatus()
        );
    }

    private String getHeaders(ContentCachingRequestWrapper requestWrapper) {
        StringBuilder stringBuilder = new StringBuilder();
        Enumeration<String> headerNames = requestWrapper.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            stringBuilder.append(headerName)
                    .append(":")
                    .append(requestWrapper.getHeader(headerName))
                    .append(";");
        }
        return stringBuilder.toString();
    }

    private String getResponseString(ContentCachingResponseWrapper responseWrapper) {
        return getStringFromBytes(responseWrapper.getContentAsByteArray());
    }

    private String getRequestString(ContentCachingRequestWrapper requestWrapper) {
        return getStringFromBytes(requestWrapper.getContentAsByteArray());
    }

    private String getStringFromBytes(byte[] bytes) {
        return normalizeSpace(new String(copyOf(bytes, bytes.length))).trim();
    }

}
