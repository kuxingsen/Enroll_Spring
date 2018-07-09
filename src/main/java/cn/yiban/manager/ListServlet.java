package cn.yiban.manager;

import cn.yiban.DAO.Enroll_jdbcTemplate;
import cn.yiban.bean.Student;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Éµ±Æ on 2018/2/24.
 */
@WebServlet(name = "ListServlet", urlPatterns = {"/List"})
public class ListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();
        ApplicationContext context =(ApplicationContext)(request.getServletContext().getAttribute("context"));
        List<Student> list = ((Enroll_jdbcTemplate)context.getBean("enroll")).enrollList();
        httpSession.setAttribute("list",list);

        request.getRequestDispatcher("EnrollList2.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
