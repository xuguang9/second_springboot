package xg.inclass.second_springboot.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/5/20 10:44
 * @Version: 1.0
 */
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        if (session == null) {
            System.out.println("没有session");
        } else {
            System.out.println("有session");
        }
        chain.doFilter(servletRequest,servletResponse);


    }

    @Override
    public void destroy() {

    }

}