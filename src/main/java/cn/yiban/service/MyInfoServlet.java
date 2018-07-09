package cn.yiban.service;

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
@WebServlet(name = "MyInfoServlet",urlPatterns = {"/MyInfo"})
public class MyInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();
        //httpSession.setAttribute("yb_userid","11489411");
        String yb_userid = (String) httpSession.getAttribute("yb_userid");
        ApplicationContext context =(ApplicationContext)(request.getServletContext().getAttribute("context"));
        Student myInfo = ((Enroll_jdbcTemplate)context.getBean("enroll")).query(yb_userid);
        httpSession.setAttribute("myInfo",myInfo);

        request.getRequestDispatcher("MyInfo.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
