package com.calathea.navi.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Slf4j
@WebFilter
public class BasicFilter implements Filter {
    /*
        Filter 인터페이스 구현체
        서블릿 실행 전에 실행됨 (그러므로 web.xml에서 url 매핑이 필요함)
        자바 웹 애플리케이션은 배포설명자파일(web.xml)을 사용하여 URL이 서블릿에 매핑되는 방법, 인증이 필요한 URL, 기타 정보를 확인
        하지만, 스프링부트에서는 이제 web.xml 파일이 없어짐
        1. 직접 객체 생성하여 등록
            WebMvcConfigure 에서 FilterRegistrationBean 을 빈으로 등록
        2. 애노테이션 이용
            WebFilter : 서블릿(javax.servlet.Filter)을 선언하는 데 사용되는 주석입니다.
            ServletComponentScan : 서블릿 구성 요소(필터, 서블릿 및 리스너)에 대한 스캔을 활성화합니다. 내장 웹 서버를 사용하는 경우에만 스캔이 수행됩니다.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("서블릿 실행 전");
        chain.doFilter(request, response);
        log.info("서블릿 실행 후");
    }
}
