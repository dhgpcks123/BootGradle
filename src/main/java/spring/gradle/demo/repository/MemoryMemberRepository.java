package spring.gradle.demo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import spring.gradle.demo.domain.Member;

public class MemoryMemberRepository implements MemberRepository{

	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L;
	
	@Override
	public Member save(Member member) {
		member.setId(++sequence);
		store.put(member.getId(), member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		
		return Optional.ofNullable(store.get(id));
		//값이 없으면 null이겠죠?
		//null 반환 될 가능성 있으면 Optional.ofNullable쓴다.
	}

	@Override
	public Optional<Member> findByName(String name) {
		return store.values().stream()
			.filter(member->member.getName().equals(name))
			.findAny();
			//이 자바스크립트 같은 이거 뭐야? 뭐야? 뭐야???

	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(store.values());
	}
	
	public void clearStore() {
		store.clear();
	}

}
