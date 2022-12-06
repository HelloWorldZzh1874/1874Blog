package com.zzh.listener;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zzh
 * @description TODO
 * @date 2022/5/20 14:19
 */
@javax.servlet.annotation.WebFilter(filterName = "sessionFilter",urlPatterns = "/*")
public class WebFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        req.getSession().setAttribute("ip",req.getRemoteHost());
        chain.doFilter(req,response);
    }
}
