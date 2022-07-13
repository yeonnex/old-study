<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.basic.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.basic.domain.member.Member" %>
<%
    // HttpServletRequest, HttpServletResponse 사용가능. JSP 도 결국은 서블릿으로 변환되기 때문
MemberRepository memberRepository = MemberRepository.getInstance();
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));
    Member member = Member.builder().username(username).age(age).build();
    memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
