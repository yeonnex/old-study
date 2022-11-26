package com.example.querydslexample.controller;

import com.example.querydslexample.dto.MemberSearchCondition;
import com.example.querydslexample.dto.MemberTeamDto;
import com.example.querydslexample.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberJpaRepository memberJpaRepository;

    // /v1/members?teamName=teamB&ageGoe=31%ageLoe41
    @GetMapping("/v1/members") // condition 의 속성들로 쿼리파리미터 사용 가능!
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.searchByWhere(condition);
    }
}
