package com.heaerie.sparrow.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.sparrow.exceptions.InvalidTokenException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import javax.ws.rs.core.MediaType;


import  com.auth0.jwt.exceptions.TokenExpiredException;


import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.*;

@WebFilter(filterName = "PillarFilter")
public class PillarFilter implements Filter {
    static final Logger logger = LogManager.getLogger();
    static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init filterConfig={}", filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("doFilter servletRequest={}", servletRequest);
        HttpServletRequest httpServletRequest = ((HttpServletRequest) servletRequest);
        HttpServletRequestWrapper newServletRequestWrapper = new HttpServletRequestWrapper(httpServletRequest);
        String token = httpServletRequest.getHeader(AuthService.AUTH_TOKEN);
        X509Certificate[] certChain = (X509Certificate[]) servletRequest.getAttribute("javax.servlet.request.X509Certificate");
        try {
            AuthToken a = AuthService.getInstance().verifyToken(token);


            HeaderMapRequestWrapper headerMapRequestWrapper =   new HeaderMapRequestWrapper(newServletRequestWrapper);
            headerMapRequestWrapper.addHeader("userId", a.getUserId());
            headerMapRequestWrapper.addHeader("role", a.getRole());
            logger.debug("doFilter userId={}, role={}", a.getUserId(), a.getRole());
            filterChain.doFilter(headerMapRequestWrapper, servletResponse);

        } catch (InvalidTokenException|TokenExpiredException e) {
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ((HttpServletResponse) servletResponse).setContentType(MediaType.APPLICATION_JSON);
        }


    }

    @Override
    public void destroy() {
        logger.info("init destroy");
    }

    private class HeaderMapRequestWrapper  extends  HttpServletRequestWrapper {

        private Map<String, String> headerMap = new HashMap<String, String>();


        public HeaderMapRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        public void  addHeader(String name, String value) {
            headerMap.put(name, value);
            logger.info(" addHeader headerMap={}",headerMap);
        }

        @Override
        public String getHeader(String name) {
             String headerValue=super.getHeader(name);
             if (headerMap.containsKey(name)) {
                 headerValue = headerMap.get(name);
             }
            return headerValue ;
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> names= Collections.list(super.getHeaders(name));
            if (headerMap.containsKey(name)) {
                names.add(headerMap.get(name));
            }
            return  Collections.enumeration(names);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names= Collections.list(super.getHeaderNames());
            for (String name: headerMap.keySet()) {
                names.add(name);
            }
            return  Collections.enumeration(names);
        }

    }
}
