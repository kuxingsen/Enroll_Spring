package cn.yiban.service;

import cn.yiban.DAO.Enroll_jdbcTemplate;
import cn.yiban.bean.Student;
import org.springframework.context.ApplicationContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@MultipartConfig(location = "D:\\Enroll_jdbcTemplate")
public class AddServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();
        String yb_userid= (String) httpSession.getAttribute("yb_userid");
        String yb_usernick= (String) httpSession.getAttribute("yb_usernick");

        ApplicationContext context =(ApplicationContext)(request.getServletContext().getAttribute("context"));
        Student student = (Student) context.getBean("student");
        student.setYb_usernick(yb_usernick);
        student.setYb_userid(yb_userid);
        String fileLocal = fileUpload(request,response,yb_usernick,student);//上传文件
        System.out.println(fileLocal);
        System.out.println(student);
        Enroll_jdbcTemplate jdbc = (Enroll_jdbcTemplate)context.getBean("enroll");
        if( jdbc.hasEnroll(yb_userid) )
        {
            jdbc.toModify(student);
        }else {
            jdbc.SaveInfo(student);
        }

        request.getRequestDispatcher("/MyInfo").forward(request,response);
    }
    private String fileUpload(HttpServletRequest request, HttpServletResponse response, String name,Student student) throws ServletException, IOException {
        //request.setCharacterEncoding("UTF-8");
        System.out.println("fileUpload.....");
        Boolean hasFile = false;
        String fileLocal = "D:\\Enroll_jdbcTemplate\\"+name;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");
        try {
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item:list)
            {
                if(item.isFormField())
                {
                    String inputName = item.getFieldName();
                    String inputValue = item.getString("utf-8");
                    System.out.println(inputName+"::"+inputValue);
                    switch ((inputName)){
                        case "phonenumber":
                            student.setPhonenumber(inputValue);break;
                        case "first":
                            student.setFirst(inputValue);break;
                        case "second":
                            student.setSecond(inputValue);break;
                        case "reason":
                            student.setReason(inputValue);break;
                        case "instest":
                            student.setInstest(inputValue);break;
                        default:
                            System.out.println("wrong...........");
                    }
                }
                else
                {
                    String fileName= item.getName();
                    fileName = fileName.substring(fileName.lastIndexOf("//")+1);
                    InputStream is = item.getInputStream();
                    File file = new File(fileLocal);
                    file.mkdirs();
                    FileOutputStream fos = new FileOutputStream(fileLocal+"\\"+fileName);

                    byte[] buf = new byte[1024];
                    int len = 0;
                    while( (len=is.read(buf))>0 ){
                        fos.write(buf,0,len);
                    }
                    is.close();
                    fos.close();
                    student.setFileLocal(fileLocal);
                    hasFile = true;
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return hasFile?fileLocal:"null";
    }
    /**
     * 无法获取到非文件组件的值，有毒啊！！！可以用fileupload
     */
    private String fileUpload1(HttpServletRequest request, HttpServletResponse response, String name,Student student) throws ServletException, IOException {

        System.out.println("fileUpload.....");
        Boolean hasFile = false;
        Collection<Part> parts = request.getParts();
        for (Part part:parts) {
            if(part.getContentType() != null)
            {
                hasFile=true;
                String fileName=getFileName(part);
                File file = new File("D://Enroll_jdbcTemplate//"+name);
                if(!file.exists())
                {
                    file.mkdirs();
                }
                part.write(name+"//"+fileName);
            }
            Collection<String> headersName=part.getHeaderNames();
            for(String headerName:headersName)
            {
                System.out.println(headerName+"::"+part.getHeader(headerName));
            }
        }
        return hasFile?"D://Enroll_jdbcTemplate//"+name:"null";
    }

    private String getFileName(Part part) {
        String contents = part.getHeader("content-disposition");
        String[] content = contents.split(";");
        for(String s:content)
        {
            if(s.trim().startsWith("filename"))
            {
                return s.substring(s.indexOf("=")+1).trim().replace("\"","");
            }
        }
        System.out.println("????");
        return "wrong";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
