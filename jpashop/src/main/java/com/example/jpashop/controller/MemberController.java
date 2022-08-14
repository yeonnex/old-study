package com.example.jpashop.controller;

import com.example.jpashop.domain.Member;
import com.example.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final ModelMapper modelMapper;
    private final MemberService memberService;

    @GetMapping("/new")
    public String joinForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "member/memberForm";
    }

    @PostMapping("/new")
    public String joinSubmit(@Validated MemberForm memberForm,
                             BindingResult errors,
                             RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            return "member/memberForm";
        }
        Member member = modelMapper.map(memberForm, Member.class);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("")
    public String memberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "member/memberList";
    }

}
