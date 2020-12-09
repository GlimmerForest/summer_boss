package com.william.boss.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author john
 */
@Slf4j
public class ResponseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("start to response wrapper");
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper((HttpServletResponse)response);
        chain.doFilter(request, wrapperResponse);
        // ContentCachingResponseWrapper 从流里读出数据后,流里就没有数据了(流里的数据只能读一次)
        // 把响应体写回到输出流
        wrapperResponse.copyBodyToResponse();
    }
}
