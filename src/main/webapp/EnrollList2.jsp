<%--
  Created by IntelliJ IDEA.
  User: 傻逼
  Date: 2018/1/26
  Time: 21:32
  To change this template use File | Settings | File Templates.
    <%@ page contentType="text/html;charset=UTF-8" %>
    <%@ page contentType="application/msexcel;charset=utf-8" %>
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>查看所有报名信息</title>
</head>
<body>

<c:set var="map" value="${sessionScope.list}"/>
<div><a href="toExcel.jsp">导出为excel</a></div>
<form method="post" action="/Delete">
    <div id="table">
        <table border="1">
            <tr>
                <td>昵称</td>
                <td>易班id</td>
                <td>手机号码</td>
                <td>第一志愿</td>
                <td>第二志愿</td>
                <td>文件</td>
                <td>删除</td>
            </tr>
            <c:forEach var="one" items="${map}">
                <tr>
                    <td>${one.yb_usernick}</td>
                    <td>${one.yb_userid}</td>
                    <td>${one.phonenumber}</td>
                    <td>${one.first}</td>
                    <td>${one.second}</td>
                    <td>${one.fileLocal}</td>
                    <td><input type="checkbox" name="delete" value="${one.yb_userid}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div>
        <button type="submit" value="删除" name="?????"/>
    </div>
</form>
</body>
</html>
