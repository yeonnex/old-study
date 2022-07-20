package hello.thymeleafbasic.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {
    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "hello <b>Spring</b>!");

        return "basic/text-basic";
    }

    @GetMapping("/unescape-text")
    public String unEscaped(Model model) {
        model.addAttribute("data", "Hi <b>JPA</b>!");
        model.addAttribute("unescapedData", "Hi <b>JPA</b>");

        return "basic/unescape-text";
    }

    @GetMapping("/variable")
    public String variable(Model model) {
        User user1 = new User("뚀먕이", 28);
        User user2 = new User("치코리타", 24);

        List<User> users = List.of(user1, user2);

        Map<String, User> userMap = new HashMap<>();
        userMap.put("user1", user1);
        userMap.put("user2", user2);

        model.addAttribute("user", user1);
        model.addAttribute("users", users);
        model.addAttribute("userMap", userMap);

        return "basic/variable";
    }

    @GetMapping("/basic-objects")
    public String basicObjects(HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        return "basic/basic-objects";
    }

    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("date", LocalDateTime.now());
        return "/basic/date";
    }

    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");

        return "/basic/link";
    }

    @GetMapping("/operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring 🌳");

        return "basic/operation";
    }

    @Component("helloBean")
    static class HelloBean{
        public String hello(String data) {
            return "Hello " + data;
        }
    }

    @AllArgsConstructor
    @Getter
    @Setter
    static class User {
        private String username;
        private int age;
    }
}
