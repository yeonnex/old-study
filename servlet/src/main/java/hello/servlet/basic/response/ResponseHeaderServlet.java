package hello.servlet.basic.response;

import org.springframework.http.HttpStatus;

import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // [status-line]
        resp.setStatus(HttpServletResponse.SC_OK);

        // [response-header]
        resp.setHeader("Content-Type", "text/plain;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("my-header", "hello");

        // [response-content 편의 메서드]
        content(resp);

        // [cookie 편의 메서드]
        cookie(resp);

        // [redirect 편의 메서드]
        redirect(resp);

        // [message body]
        resp.getWriter().write("ok");
    }

    private void content(HttpServletResponse resp) {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");
        // resp.setContentLength 생략시 자동 생성
    }

    private void cookie(HttpServletResponse resp) {
        // Set-Cookie: myCookie=good; Max-Age=600;
        // resp.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); // 600 초
        resp.addCookie(cookie);
    }

    private void redirect(HttpServletResponse resp) throws IOException {
        // Status Code 302
        // Location: /basic/hello-form.html

//         resp.setStatus(HttpServletResponse.SC_FOUND); // 302
//         resp.setHeader("Location", "/basic/hello-form.html");

        resp.sendRedirect("/basic/hello-form.html");
    }
}
