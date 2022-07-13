<%@ page import="hello.servlet.basic.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.basic.domain.member.Member" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> all = memberRepository.findAll();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table>
        <thead>
            <th>id</th>
            <th>username</th>
            <th>age</th>
        </thead>
        <tbody>
        <%
            for (Member member : all) {
                out.write("<tr>");
                out.write("<td>" + member.getId() + "</td>");
                out.write("<td>" + member.getUsername() + "</td>");
                out.write("<td>" + member.getAge() + "</td>");
                out.write("</tr>");
            }
        %>
        </tbody>
    </table>
</body>
</html>
