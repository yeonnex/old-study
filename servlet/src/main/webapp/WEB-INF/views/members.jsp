<%@ page import="hello.servlet.basic.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <table>
        <thead>
            <th>id</th>
            <th>username</th>
            <th>age</th>
        </thead>
        <tbody>
            <c:forEach var="member" items ="${members}">
                <td>${member.id}</td>
                <td>${member.username}</td>
                <td>${member.age}</td>
            </c:forEach>
        </tbody>
    </table>

</head>
<body>

</body>
</html>
