package cn.yiban.DAO;

import cn.yiban.bean.Student;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//失败，不知道为什么说表不存在
@Transactional
public class Enroll_jdbcTemplate {
    public Enroll_jdbcTemplate(){}
    @Resource(name = "dataSource")
    private DriverManagerDataSource dataSource;
    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }
    public int SaveInfo(Student student)
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "insert into EnrollList(yb_userid,yb_usernick,phonenumber,first,second,instest,reason,fileLocal) values"
                +"('"+student.getYb_userid()+"','"+student.getYb_usernick()+"','"+student.getPhonenumber()+"','"+student.getFirst()+"','"+student.getSecond()+
                "','"+student.getInstest()+"','"+student.getReason()+"','"+student.getFileLocal()+"')";

     //   String sql = "select count(*) from EnrollList";
       // System.out.println(jdbcTemplate.queryForObject(sql,Integer.class));
        /*sql server不可以使用占位符
        String sql = "insert into EnrollList value(?,?,?,?,?,?,?,?)";

        int rows = jdbcTemplate.update(sql,student.getYb_userid(),student.getYb_usernick(),student.getPhonenumber(),student.getFirst()
        ,student.getSecond(),student.getInstest(),student.getReason(),student.getReason());
        */
        int rows = jdbcTemplate.update(sql);
        System.out.println(rows);
        return rows;
    }
    public List<Student> enrollList()
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from EnrollList";
        List<Student> students = jdbcTemplate.query(sql,new StudentList());
        System.out.println(students);
        return students;
    }
    public Student query(String yb_userid)
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from EnrollList where yb_userid='"+yb_userid+"'";
        System.out.println(sql);
        Student student = jdbcTemplate.queryForObject(sql,new StudentList());
        System.out.println(student);
        return student;
    }
    public int delete(String yb_userid)
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "delete from EnrollList where yb_userid='"+yb_userid+"'";
        System.out.println(sql);
        int num = jdbcTemplate.update(sql);
        System.out.println(num);
        return num;

    }
    public boolean hasEnroll(String yb_userid)
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select count(*) from EnrollList where yb_userid='"+yb_userid+"'";
        System.out.println(sql);
        int num = jdbcTemplate.queryForObject(sql,Integer.class);
        return num > 0;
    }
    public boolean toModify(Student student)
    {
        delete(student.getYb_userid());
        //System.out.println(10/0);//测试事务回滚
        int num = SaveInfo(student);
        return num > 0;
    }
}
class StudentList implements RowMapper<Student>{

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        String yb_userid = resultSet.getString("yb_userid");
        String yb_usernick = resultSet.getString("yb_usernick");
        String phonenumber = resultSet.getString("phonenumber");
        String first = resultSet.getString("first");
        String second = resultSet.getString("second");
        String instest = resultSet.getString("instest");
        String reason = resultSet.getString("reason");
        String fileLocal = resultSet.getString("fileLocal");

        Student student = new Student();
        student.setAll(yb_userid,yb_usernick,phonenumber,instest,first,second,reason,fileLocal);
        System.out.println(student);
        return student;
    }
}