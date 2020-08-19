package com.example.ebanking.filters;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Component
public class LoginFilter extends OncePerRequestFilter {

    private Set<String> excludeUrlPatterns = new HashSet<>(Arrays.asList("/login"));
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String path= request.getRequestURI();

        if(path.endsWith(".css")||path.endsWith(".js")){
            chain.doFilter(request,response);
            return;
        }

        if(session==null || session.getAttribute("client")==null) {
            response.sendRedirect("login");
        } else {
            chain.doFilter(request,response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludeUrlPatterns.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }
}
