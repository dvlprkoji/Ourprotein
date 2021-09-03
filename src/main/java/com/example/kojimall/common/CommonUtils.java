package com.example.kojimall.common;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Service
public class CommonUtils {
    public void printParams(HttpServletRequest request) {
        System.out.println("======================[ " + request.getRequestURI() + " ]========================");
        Enumeration<String> parameterNames = request.getParameterNames();
        parameterNames.asIterator().forEachRemaining(param -> System.out.println(param + " = " + request.getParameter(param)));
        System.out.println("===========================================================");
    }
}
