package com.dengh.explore.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author dengH
 * @title: FilterDemo
 * @description: TODO
 * @date 2019/9/10 10:58
 */
public class FilterDemo implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("filterDemo begin");
        chain.doFilter(request, response);
        System.out.println("filterDemo end");
    }

    @Override
    public void destroy() {

    }
}
