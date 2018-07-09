package cn.yiban.service;

import cn.yiban.API.Authorize;
import cn.yiban.API.DemoApi;
import cn.yiban.API.User;
import cn.yiban.DAO.Enroll_jdbcTemplate;
import cn.yiban.bean.AppContent;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 傻逼 on 2018/2/13.
 */
public class authServlet extends HttpServlet {
    private static  final Logger LOGGER = Logger.getLogger(authServlet.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        // 记录debug级别的信息
        LOGGER.debug("This is debug message.+++");
        // 记录info级别的信息
        LOGGER.info("This is info message.---");
        // 记录warn级别的信息
        LOGGER.info("This is warn message.***");
        // 记录error级别的信息
        LOGGER.error("This is error message.///");
        */
        response.setContentType("text/html;charset=UTF-8");
        HttpSession httpSession = request.getSession();
        Authorize au = new Authorize(AppContent.AppID,AppContent.AppSecret);
        String code = request.getParameter("code");
        if(code == null||code.isEmpty())//先判断是否已授权 code为授权令牌
        {
            String url=au.forwardurl(AppContent.BACK_URL,"what",Authorize.DISPLAY_TAG_T.WEB);
            //System.out.println("authServlet::"+url);
            response.sendRedirect(url);
        }
        else{
            JSONObject queryJson = JSONObject.fromObject(au.querytoken(code,AppContent.BACK_URL));//获得授权凭证
            //System.out.println(queryJson);
            String access_token = queryJson.get("access_token").toString();
            String yb_userid = queryJson.get("userid").toString();
            httpSession.setAttribute("access_token",access_token);
            httpSession.setAttribute("yb_userid",yb_userid);

            /**
             * 发送欢迎消息
             */
            JSONObject msgJson= JSONObject.fromObject(DemoApi.letter(access_token,yb_userid,"欢迎进入易班招新报名系统","user"));
            System.out.println("authServlet::"+msgJson);

            /**
             * 保存个人信息
             */
            ApplicationContext context =(ApplicationContext)(request.getServletContext().getAttribute("context"));
            User user = (User) context.getBean("user");
            user.setToken(access_token);
            JSONObject userJson = JSONObject.fromObject(user.me());
            JSONObject infoJSON=userJson.getJSONObject("info");
            httpSession.setAttribute("yb_userhead",infoJSON.get("yb_userhead").toString());
            httpSession.setAttribute("yb_usernick",infoJSON.get("yb_usernick").toString());
            Enroll_jdbcTemplate jdbc = (Enroll_jdbcTemplate)context.getBean("enroll");
            if( jdbc.hasEnroll(yb_userid) )
            {
                System.out.println("已报名");
                request.getRequestDispatcher("/MyInfo").forward(request,response);
            }else {
                System.out.println("未报名");
                request.getRequestDispatcher("toEnroll.jsp").forward(request,response);
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
