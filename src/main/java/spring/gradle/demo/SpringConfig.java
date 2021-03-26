package spring.gradle.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.gradle.demo.repository.MemberRepository;
import spring.gradle.demo.repository.MemoryMemberRepository;
import spring.gradle.demo.service.MemberService;

@Configuration
public class SpringConfig {

	@Bean
	public MemberService memberService() {
		return new MemberService(memberRepository());
	}
	@Bean
	public MemberRepository memberRepository() {
		return new MemoryMemberRepository();
	}
}
