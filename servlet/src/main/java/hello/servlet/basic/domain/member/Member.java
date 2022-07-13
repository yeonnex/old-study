package hello.servlet.basic.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Member {
    private Long id;
    private String username;
    private Integer age;

    public Member(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
    public Member() {

    }
}
