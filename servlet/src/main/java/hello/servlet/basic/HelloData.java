package hello.servlet.basic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class HelloData {
    private String username;
    private Integer age;
}
