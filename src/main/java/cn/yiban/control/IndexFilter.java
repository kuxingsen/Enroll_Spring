package cn.yiban.control;

import cn.yiban.DAO.Enroll_jdbcTemplate;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 傻逼 on 2018/2/13.
 */
public class IndexFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession();
        String access_token = (String)session.getAttribute("access_token");

        if( access_token==null ||access_token.isEmpty())
        {
            System.out.println("没有access_token,重新验证");
            request.getRequestDispatcher("/authServlet").forward(request,response);
        }
        else {
            chain.doFilter(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
