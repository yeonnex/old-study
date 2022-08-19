package com.example.datajpa.repository;

import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    void memberTest() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(member).isEqualTo(savedMember);
        assertEquals(savedMember.getId(), findMember.getId());

    }

    @Test
    void greaterThan() {
        Member ming1 = new Member("ming", 17);
        Member ming2 = new Member("ming", 24);
        memberRepository.save(ming1);
        memberRepository.save(ming2);

        List<Member> ming = memberRepository.findByUsernameAndAgeGreaterThan("ming", 20);

        assertEquals(ming.get(0).getUsername(), "ming");
        assertEquals(ming.get(0).getAge(), 24);
    }

    @Test
    void findUserAnnoQuery() {
        Member member = new Member("ming", 24);
        memberRepository.save(member);
        List<Member> ming = memberRepository.findUser("ming", 24);

        assertEquals(ming.get(0).getAge(), 24);
    }

    @Test
    void fineUsernameList() {
        Member ming = new Member("ming", 24);
        memberRepository.save(ming);
        Member mang = new Member("mang", 28);
        memberRepository.save(mang);

        List<String> usernameList = memberRepository.findUsernameList();

        usernameList.forEach(System.out::println);

    }

    @Test
    void findDto() {
        Team apple = new Team("Apple");
        Team google = new Team("Google");
        teamRepository.save(apple);
        teamRepository.save(google);

        Member ming = new Member("ming", 24, apple);
        memberRepository.save(ming);
        Member mang = new Member("mang", 28, google);
        memberRepository.save(mang);

        List<MemberDto> memberDtoList = memberRepository.findMemberDto();
        assertThat(memberDtoList.get(0)).isInstanceOf(MemberDto.class);
        memberDtoList.forEach(System.out::println);
    }

    @Test
    void findByList() {
        Member ming = new Member("ming", 24);
        memberRepository.save(ming);
        Member som = new Member("som", 28);
        memberRepository.save(som);
        Member mang = new Member("mang", 33);
        memberRepository.save(mang);

        List<Member> list = memberRepository.findByList(List.of("ming", "mang"));
        assertThat(list.size()).isEqualTo(2);

        System.out.println(list);
    }

    @Test
    void paging() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3,
                Sort.by(Sort.Direction.DESC, "username"));


        // when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        Page<MemberDto> map = page.map(MemberDto::new); // 이걸 반환하자!



        // then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        content.forEach(System.out::println);
        System.out.println("total elements = " + totalElements);

        assertEquals(content.size(), 3);
        assertEquals(page.getTotalElements(), 5);
        assertEquals(page.getNumber(), 0);
        assertEquals(page.getTotalPages(),2); // 전체 페이지 개수
        assertThat(page.isFirst()).isTrue(); // 첫번째 페이지인지의 여부
        assertThat(page.hasNext()).isTrue();


    }

}