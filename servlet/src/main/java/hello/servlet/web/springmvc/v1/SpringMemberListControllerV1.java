package hello.servlet.web.springmvc.v1;

import hello.servlet.basic.domain.member.Member;
import hello.servlet.basic.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class SpringMemberListControllerV1 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members/members")
    public ModelAndView process(Map<String, String> paramMap) {

        List<Member> all = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members", all);
        return mv;
    }

}
