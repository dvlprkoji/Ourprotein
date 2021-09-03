package com.example.kojimall.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/register", "/login", "/logout", "/css/*", "/fonts/*", "/style.css", "/js/*", "/mail/*", "/scss/*", "/item"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("인증 체크 필터 동작");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if (isLoginCheckPath(requestURI)) {
            log.info("인증 체크 로직 실행");
            HttpSession session = httpServletRequest.getSession();
            if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                log.info("미인증 사용자 요청");
                httpServletResponse.sendRedirect("/login?redirectURL=" + requestURI);
                return;
            }
        }
        chain.doFilter(request,response);
    }

    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }

}
