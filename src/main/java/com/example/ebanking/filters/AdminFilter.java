package com.example.ebanking.filters;

import com.example.ebanking.model.ClientModel;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    public void init(FilterConfig arg0) throws ServletException {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();

        ClientModel user = (ClientModel) session.getAttribute("client");


        if(user==null) {
            response.sendRedirect("login");
        } else if(!user.getRole().equals("admin")) {
            response.sendRedirect("home");
        } else {
            chain.doFilter(request,response);
        }
    }

    public void destroy() {}


}
