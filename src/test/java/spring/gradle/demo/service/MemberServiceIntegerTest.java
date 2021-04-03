package spring.gradle.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import spring.gradle.demo.domain.Member;
import spring.gradle.demo.repository.MemberRepository;

@SpringBootTest
//스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional
//테스트 시작 전에 트랙잰셕을 시작하고, 테스트 완료 후에 항상 롤백한다.
class MemberServiceIntegerTest {
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;

	/*
	@AfterEach public void afterEach() { memberRepository.deleteAll(); }
	이걸 또 만들어야하나.. 귀찮잖아. @Transactional을 쓰면 됨
	commit하기 전에 db에 반영이 안 됨.
	테스트 끝나고 나서 롤백시켜주는 개념인거지.
	@Transactional 어노테이션 붙임.
	 */
	@Test
//	@Commit 롤백 Transactional안 쓰고 싶다. Commit 어노테이션 붙여주면 됨.
	void 회원가입() {
		// 에에엥!? 실제 동작하는 코드는 한글로 쓰기 애매하지.
		// 근데 테스트는 한글로 직관적으로 많이 쓴다...

		//given
		Member member = new Member();
		member.setName("hello");
		//when
		Long saveId = memberService.join(member);
		//then
		Member findMember = memberService.findOne(saveId).get();
		assertThat(member.getName()).isEqualTo(findMember.getName());
	}
	
	@Test
	public void 중복_회원_예외() {
		//given
		Member member1 = new Member();
		member1.setName("spring");
		
		Member member2 = new Member();
		member2.setName("spring");
		//when
		memberService.join(member1);
		/*
		try {
			memberService.join(member2);
			fail("에외가 발생해야 합니다.");
		}catch(IllegalStateException e) {
			Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
		}
		 */
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
//		assertThrows(NullPointerException.class, () -> memberService.join(member2));
		//then
		
	}
}
