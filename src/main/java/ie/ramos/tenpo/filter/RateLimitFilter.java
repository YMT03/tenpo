package ie.ramos.tenpo.filter;


import ie.ramos.tenpo.exception.domain.TooManyRequestException;
import ie.ramos.tenpo.mapper.ErrorMapper;
import ie.ramos.tenpo.util.JsonWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
@Order(1)
public class RateLimitFilter extends OncePerRequestFilter {

    private RateLimitService rateLimitService;
    private JsonWrapper jsonWrapper;
    private ErrorMapper errorMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            rateLimitService.check();
            filterChain.doFilter(request, response);
        } catch (TooManyRequestException e) {
            response.getWriter().write(jsonWrapper.toJsonString(errorMapper.mapToDTO(e)));
            response.setStatus(e.getHttpStatus().value());
        }
    }
}
