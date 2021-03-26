package spring.gradle.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.gradle.demo.domain.Member;
import spring.gradle.demo.repository.MemberRepository;

public class MemberService {
	
	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	/*
	 * 회원 가입
	 */
	public Long join(Member member) {
		
		validateDuplicateMember(member);// 중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		// 같은 이름이 있는 중복 회원X
//		Optional<Member> result = memberRepository.findByName(member.getName());
//		result.ifPresent(m->{
//			throw new IllegalStateException("이미 존재하는 회원입니다");
//		});
		memberRepository.findByName(member.getName())
			.ifPresent(m->{
				throw new IllegalStateException("이미 존재하는 회원입니다");
			});
		//람다식 이런 식으로 쓴다는 게 너무 놀랍다;;
	}
	
	/*
	 * 전체 회원 조회
	 */
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
	public Optional<Member> findOne(Long memberId){
		return memberRepository.findById(memberId);
	}
}