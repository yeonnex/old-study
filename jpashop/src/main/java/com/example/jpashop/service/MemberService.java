package com.example.jpashop.service;

import com.example.jpashop.api.MemberApiController;
import com.example.jpashop.domain.Member;
import com.example.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepositoryOld memberRepository;
    private final ModelMapper modelMapper;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicatedMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 전체 조회
     */

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public List<MemberApiController.MemberResponse> findAllMemberResponse() {
        return memberRepository.findAll()
                .stream()
                .map(m -> modelMapper.map(m, MemberApiController.MemberResponse.class)).collect(Collectors.toList());
    }

    // 회원 단건 조회

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }


    private void validateDuplicatedMember(Member member) {
        String name = member.getName();
        List<Member> savedMember = memberRepository.findByName(name);
        if (!savedMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    public void update(Long id, MemberApiController.UpdateMemberRequest request) {

    }
}
