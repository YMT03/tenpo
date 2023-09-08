package ie.ramos.tenpo.filter.ratelimit;


import ie.ramos.tenpo.dto.out.ErrorDTO;
import ie.ramos.tenpo.exception.domain.ApiException;
import ie.ramos.tenpo.exception.domain.TooManyRequestException;
import ie.ramos.tenpo.mapper.ErrorMapper;
import ie.ramos.tenpo.util.JsonWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RateLimitFilterTest {

    @InjectMocks
    private RateLimitFilter filter;
    @Mock
    private RateLimitService rateLimitService;
    @Mock
    private ErrorMapper errorMapper;
    @Mock
    private JsonWrapper jsonWrapper;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private PrintWriter printWriter;


    @Test
    public void testDoFilterInternal_OK() throws ServletException, IOException {
        filter.doFilterInternal(request, response, filterChain);
        verify(rateLimitService).check();
        verify(errorMapper, never()).mapToDTO(any(ApiException.class));
        verify(jsonWrapper, never()).toJsonString(any());
        verify(response, never()).getWriter();
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    public void testDoFilterInternal_ThrowsTooManyRequestException() throws ServletException, IOException {
        var exception = new TooManyRequestException();
        var errorDTO = new ErrorDTO();
        var jsonError = "{some-error-dto}";
        doThrow(exception)
                .when(rateLimitService)
                .check();

        when(response.getWriter())
                .thenReturn(printWriter);
        when(errorMapper.mapToDTO(exception))
                .thenReturn(errorDTO);
        when(jsonWrapper.toJsonString(errorDTO))
                .thenReturn(jsonError);

        filter.doFilterInternal(request, response, filterChain);

        verify(rateLimitService).check();
        verify(errorMapper).mapToDTO(exception);
        verify(response).getWriter();
        verify(printWriter).write(jsonError);
        verify(response).setStatus(429);
        verify(jsonWrapper).toJsonString(errorDTO);
    }
}
