package com.capstone.dayj.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Log4j2
@Component
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        chain.doFilter(request, response); // 다음 필터 또는 서블릿 호출
        
        long duration = System.currentTimeMillis() - startTime;
        
        String coloredUri = "\u001B[01m\u001B[32m" + uri + "\u001B[0m"; // 초록색
        String coloredDuration = "\u001B[01m\u001B[33m" + duration + " ms" + "\u001B[0m"; // 노란색
        log.info("\u001B[40m\u001B[35m" + "Request URI: {}" + "\u001B[40m\u001B[35m" + ", Duration: {}", coloredUri, coloredDuration);
    }
}