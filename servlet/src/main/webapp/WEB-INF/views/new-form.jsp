<%--
  Created by IntelliJ IDEA.
  User: yeonnex
  Date: 2022/07/13
  Time: 5:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%--상대경로 사용. [현재 URL이 속한 계층 경로 + /save ]--%>
    <form action="save" method="post">
        username: <input type="text" name="username"/>
        age:      <input type="text" name="age" />
        <button type="submit">제출하기</button>
    </form>
</body>
</html>
