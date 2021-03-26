package spring.gradle.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spring.gradle.demo.domain.Member;
import spring.gradle.demo.repository.MemoryMemberRepository;

class MemberServiceTest {
	
	MemberService memberService;
	MemoryMemberRepository memberRepository;
	
	@BeforeEach
	public void beforeEach() {
		memberRepository = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
		//이런 걸 DI라고 한다는데.....
	}
	
	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
	}
	
	@Test
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

	@Test
	void testFindMembers() {
		//given
		
		//when
		
		//then
	}

	@Test
	void testFindOne() {
		//given
		
		//when
		
		//then
	}

}
