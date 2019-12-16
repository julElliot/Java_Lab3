<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="app.bean.Client" %><%--
  Created by IntelliJ IDEA.
  User: German
  Date: 09.12.2019
  Time: 6:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clients list</title>
</head>
<body>
    Hi, everybody
    <c:if test="${clients != null && !clients.isEmpty()}">
        <table border="1">
            <caption>Clients table</caption>
            <tr>
                <th>Id</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Money</th>
                <th>TableId</th>
                <th>Number</th>
                <th>IsFree</th>
            </tr>

            <c:forEach var="client" items="${clients}">
                <tr>
                    <td>${client.getId()}</td>
                    <td>${client.getFirstName()}</td>
                    <td>${client.getLastName()}</td>
                    <td>${client.getMoney()}</td>
                    <td>${client.getTable().getId}</td>
                    <td>${client.getTable().getNumber}</td>
                    <td>${client.getTable().isFree}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${clients == null || clients.isEmpty()}">
        <p>Empty list</p>
    </c:if>
    <c:if test="${pageIndex != 1}">
        <a href="/list?pageIndex=1">Start</a>
        <a href="/list?pageIndex=${pageIndex - 1}"><</a>
    </c:if>
    <c:if test="${pageIndex != pageCount}">
        <a href="/list?pageIndex=${pageIndex + 1}">></a>
    </c:if>
</body>
</html>
