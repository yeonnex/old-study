package com.example.jpashop.api;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Member;
import com.example.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = modelMapper.map(request, Member.class);
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}/edit")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id, @RequestBody UpdateMemberRequest request) {
        memberService.update(id, request);
        memberService.findOne(id);
        return new UpdateMemberResponse(id);
    }

    @Getter
    @Setter
    static class CreateMemberRequest {
        @NotBlank
        private String name;
        private Address address;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    static class CreateMemberResponse {
        private final Long id;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class UpdateMemberResponse {
        private final Long id;
    }

    @Getter
    @Setter
    public static class UpdateMemberRequest {
        private Address address;
    }
}
