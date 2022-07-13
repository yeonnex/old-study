package hello.servlet.basic.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {
    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("member 저장")
    void save() {
        // given
        Member seoyeon = Member.builder().username("seoyeon").age(24).build();
        Member munchae = Member.builder().username("munchae").age(24).build();
        // when
        memberRepository.save(seoyeon);
        memberRepository.save(munchae);

        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

}