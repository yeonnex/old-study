package hello.itemservice.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {
    @Autowired
    MessageSource messageSource;

    @Test
    void helloMsg() {
        System.out.println(messageSource.getMessage("hello", null, Locale.US));
    }

    @Test
    void argumentMsg(){
        System.out.println(messageSource.getMessage("hello.name", new Object[]{"seoyeon"}, Locale.US));
    }
}
