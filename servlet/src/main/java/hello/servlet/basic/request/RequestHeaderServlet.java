package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

@WebServlet(name = "requestHeaderTest", urlPatterns = "/request-header-test")
public class RequestHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printStartLine(req); // 요청 시작 라인
        printHeaders(req); // 요청 header 정보
        printHeaderUtils(req); // 요청 header 편의 조회
    }

    private void printStartLine(HttpServletRequest req) {
        System.out.println("--- REQUEST Start Line ---");
        System.out.println("req.getMethod() = " + req.getMethod()); // GET
        System.out.println("req.getProtocol() = " + req.getProtocol()); // HTTP/1.1
        System.out.println("req.getScheme() = " + req.getScheme()); // http
        System.out.println("req.getRequestURL() = " + req.getRequestURL()); // http://localhost:8080/request-header-test
        System.out.println("req.getRequestURI() = " + req.getRequestURI()); // /request-header-test
        System.out.println("req.getQueryString() = " + req.getQueryString()); // username=seoyeon
        System.out.println("req.isSecure() = " + req.isSecure()); // https 사용 유무
        System.out.println("--- REQUEST Line End ---");
        System.out.println();
    }

    private void printHeaderUtils(HttpServletRequest req) {
        System.out.println("--- Header 편의 조회 시작 ---");
        System.out.println("[ Host 편의 조회 ]");
        System.out.println("req.getServername() = " + req.getServerName()); // localhost
        System.out.println("req.getServerPort() = " + req.getServerPort()); // 8080
        System.out.println();

        System.out.println("[ Accept-Language 편의 조회 ]");
        System.out.println("req.getLocale() = " + req.getLocale()); // ko
        System.out.println();

        System.out.println("[ Cookie 편의 조회 ]");
        if (req.getCookies()!=null) {
            Arrays.stream(req.getCookies()).forEach(c -> System.out.println(c.getName() + ": " + c.getValue()));
        }else {
            System.out.println("쿠키 없음");
        }

        System.out.println();

        System.out.println("[ Content 편의 조회 ]");
        System.out.println("req.getContentType() = " + req.getContentType());
        System.out.println("req.getContentLength() = " + req.getContentLength());
        System.out.println("req.getCharacterEncoding() = " + req.getCharacterEncoding());

        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();

    }

    private void printHeaders(HttpServletRequest req) {
        System.out.println("--- REQUEST Headers ---");
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + req.getHeader(headerName));
        }
        System.out.println("--- REQUEST Headers End ---");
        System.out.println();
    }
}
