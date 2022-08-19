package com.example.datajpa.dto;

import com.example.datajpa.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Member m) {
        id = m.getId();
        username = m.getUsername();
        teamName = m.getTeam().getName();
    }
}
