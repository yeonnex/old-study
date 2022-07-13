package hello.servlet.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "memberServlet", urlPatterns = "/servlet-form")
public class MemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        PrintWriter w = resp.getWriter();
        w.write(
                "<html>" +
                        "<form action=\"/servlet/members/save\" method=\"post\">" +
                        "username" + "<input type=\"text\" name=\"username\">" +
                        "age" + "<input type=\"text\" name=\"age\"> " +
                        "<button type=\"submit\">" + "제출하기" + "</button>" +
                        "</form>" +
                    "</html>"
        );
    }
}
