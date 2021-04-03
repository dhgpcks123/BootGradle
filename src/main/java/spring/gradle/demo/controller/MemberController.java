package spring.gradle.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import spring.gradle.demo.domain.Member;
import spring.gradle.demo.service.MemberService;

@Controller
public class MemberController {
	
	private final MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/members/new")
	public String createForm() {
		return "members/createMemberForm";
	}
	//페이지 이동은 get으로 데이터 넘겨서 등록하는 건 post로 했넹? 똑같은 url했는데 같은 페이지로 넘겼네.
	@PostMapping("/members/new")
	public String create(MemberForm form) {
		Member member = new Member();
		member.setName(form.getName());
		
		memberService.join(member);
		return "redirect:/";
	}
	
	@GetMapping("/members")
	public String list(Model model) {
		List<Member> members =memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
	}
}
