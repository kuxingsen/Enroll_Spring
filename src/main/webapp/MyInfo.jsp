<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 傻逼
  Date: 2018/2/24
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:set var="myInfo" value="${sessionScope.myInfo}"/>
<table>
    <tr>
        <td>昵称</td>
        <td>${myInfo.yb_usernick}</td>
    </tr>
    <tr>
        <td>易班id</td>
        <td>${myInfo.yb_userid}</td>
    </tr>
    <tr>
        <td>手机号码</td>
        <td>${myInfo.phonenumber}</td>
    </tr>
    <tr>
        <td>第一志愿</td>
        <td>${myInfo.first}</td>
    </tr>
    <tr>
        <td>第二志愿</td>
        <td>${myInfo.second}</td>
    </tr>
    <tr>
        <td>文件</td>
        <td>${myInfo.fileLocal}</td>
    </tr>
</table>
<a href="toEnroll.jsp">修改</a>
</body>
</html>
