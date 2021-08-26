package com.calathea.navi.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
public class GFBFilter extends GenericFilterBean {
    /*
        spring은 filter에서 spring config 설정 정보를 쉽게 처리하기 위한 GenericFilterBean을 제공
        Filter를 구현한 추상 클래스
        getFilterConfig()나 getEnvironment() 등을 제공
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info(getEnvironment().getActiveProfiles().toString());
        log.info("서블릿 실행 전");
        chain.doFilter(request, response);
        log.info("서블릿 실행 후");
    }
}
