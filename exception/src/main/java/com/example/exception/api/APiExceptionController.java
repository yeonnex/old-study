package com.example.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APiExceptionController {
    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable Long id) {
         if (id.equals("ex")) {
             throw new RuntimeException("잘못된 사용자");
         }
         return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private Long memberId;
        private String name;
    }
}
