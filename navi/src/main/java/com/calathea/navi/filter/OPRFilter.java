package com.calathea.navi.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class OPRFilter extends OncePerRequestFilter {
    /*
        모든 서블릿 컨테이너에서 요청 디스패치당 단일 실행을 보장하는 것을 목표로 하는 필터 기본 클래스입니다. HttpServletRequest 및 HttpServletResponse 인수와 함께 doFilterInternal 메소드를 제공합니다.
        public abstract class OncePerRequestFilter extends GenericFilterBean
        doFilter 는 final 로 정의되어 있어서 사용할 수 없음
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("서블릿 실행 전");
        filterChain.doFilter(request, response);
        log.info("서블릿 실행 후");
    }
}
