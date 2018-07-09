package cn.yiban.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Éµ±Æ on 2018/2/13.
 */
@Component(value = "student")
@Scope(value = "singleton")
public class Student {
    private String yb_userid;
    private String yb_usernick;
    private String yb_userhead;
    private String phonenumber;
    private String instest;
    private String first;
    private String second;
    private String reason;
    private String fileLocal;

    public Student() {
        System.out.println("new Student()......");
    }
    public void setAll(String yb_userid,String yb_usernick,String phonenumber,String instest,String first,String second,String reason,String file)
    {
        setYb_userid(yb_userid);
        setYb_usernick(yb_usernick);
        setPhonenumber(phonenumber);
        setInstest( instest);
        setFirst( first);
        setSecond(second);
        setReason(reason);
        setFileLocal(file);
    }
    public String toString()
    {
        return yb_userid+yb_usernick+phonenumber+instest+first+second+reason+ fileLocal;
    }
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getInstest() {
        return instest;
    }

    public void setInstest(String instest) {
        this.instest = instest;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getYb_userhead() {
        return yb_userhead;
    }

    public void setYb_userhead(String yb_userhead) {
        this.yb_userhead = yb_userhead;
    }

    public String getFileLocal() {
        return fileLocal;
    }

    public void setFileLocal(String fileLocal) {
        this.fileLocal = fileLocal;
    }

    public void setYb_userid(String yb_userid) {
        this.yb_userid = yb_userid;
    }
    public String getYb_userid() {
        return yb_userid;
    }

    public void setYb_usernick(String yb_usernick) {
        this.yb_usernick = yb_usernick;
    }
    public String getYb_usernick() {
        return yb_usernick;
    }
}
