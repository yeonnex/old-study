package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "requestParam", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[전체 파리미터 조회] - start");
        req.getParameterNames().asIterator().forEachRemaining(p -> System.out.println(p+ "=" + req.getParameter(p)));
        System.out.println("[전체 파리미터 조회] - end");
        System.out.println();
    }
}
