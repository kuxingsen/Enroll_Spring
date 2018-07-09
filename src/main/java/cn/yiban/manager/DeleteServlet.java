package cn.yiban.manager;

import cn.yiban.DAO.Enroll_jdbcTemplate;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Éµ±Æ on 2018/2/25.
 */
@WebServlet(name = "DeleteServlet",urlPatterns = {"/Delete"})
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String[] deletes = request.getParameterValues("delete");
        ApplicationContext context =(ApplicationContext)(request.getServletContext().getAttribute("context"));
        for(String yb_userid:deletes)
        {
            int num =((Enroll_jdbcTemplate)context.getBean("enroll")).delete(yb_userid);
            System.out.println(num);
        }

        request.getRequestDispatcher("/List").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
