package software.jevera.configuration;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;

@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if(httpServletRequest.getRequestURI().startsWith("/api/")){
            if(httpServletRequest.getSession() == null || httpServletRequest.getSession().getAttribute("user") == null){
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setStatus(401);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
