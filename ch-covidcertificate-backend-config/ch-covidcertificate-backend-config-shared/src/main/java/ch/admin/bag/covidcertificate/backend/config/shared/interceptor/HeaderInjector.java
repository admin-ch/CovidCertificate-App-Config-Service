package ch.admin.bag.covidcertificate.backend.config.shared.interceptor;

import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class HeaderInjector implements HandlerInterceptor {
    private final Map<String, String> headers;

    public HeaderInjector(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (headers != null) {
            for (Entry<String, String> header : headers.entrySet()) {
                response.setHeader(header.getKey(), header.getValue());
            }
        }
        return true;
    }
}
