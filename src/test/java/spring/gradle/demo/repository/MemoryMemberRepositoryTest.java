package spring.gradle.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import spring.gradle.demo.domain.Member;

class MemoryMemberRepositoryTest {
	
	MemoryMemberRepository repository = new MemoryMemberRepository();
	
	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}
	
	//save
	@Test
	public void save() {
		Member member = new Member();
		member.setName("spring");
		repository.save(member);
		
		Member result = repository.findById(member.getId()).get();
//		System.out.println("result = " + (result==member));
//		Assertions.assertEquals(member, result);
//		Assertions.assertEquals(member, null);
		//이거 없는데? API버전이 다른가... 그래서 assertj 3.4.1 다운 받았음
		assertThat(member).isEqualTo(result);
	}
	//findByName 테스트
	@Test
	public void FindByName() {
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		Member result = repository.findByName("spring1").get();
		assertThat(result).isEqualTo(member1);
	}
	
	@Test
	public void findAll() {
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring1");
		repository.save(member2);
		
		List<Member> result = repository.findAll();
		
		assertThat(result.size()).isEqualTo(2);
	}
	
}
