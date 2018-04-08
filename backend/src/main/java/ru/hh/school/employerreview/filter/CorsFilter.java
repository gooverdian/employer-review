package ru.hh.school.employerreview.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CorsFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {

    HttpServletResponse res = (HttpServletResponse) response;
    res.addHeader("Access-Control-Allow-Origin", "*");
    res.addHeader("Access-Control-Allow-Credentials", "true");
    res.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
    res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void destroy() {
  }
}
