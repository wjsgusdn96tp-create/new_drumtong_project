package kr.co.iei.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value="/loginFrm")
	public String loginFrm() {
		return "member/login";
	}// loginFrm
	
	@PostMapping(value="/login")
	public String login(Member m, Model model, HttpSession session) {
		Member member = memberService.login(m);
		System.out.println(member);
		session.setAttribute("member", member);
		return "redirect:/";
	}// login
	
	@GetMapping(value="/joinFrm")
	public String joinFrm() {
		return "member/joinFrm";
	}// joinFrm
	
	@PostMapping(value="/join")
	public String join(Member m, Model model, HttpSession session) {
	    // 1. 세션에서 인증 성공 여부 가져오기
	    Boolean emailVerified = (Boolean) session.getAttribute("emailVerified");
	    // 2. 인증 안됐을 경우
	    if(emailVerified == null || !emailVerified) {
	        model.addAttribute("title", "회원가입 실패");
	        model.addAttribute("text", "이메일 인증을 완료해야 가입할 수 있습니다.");
	        model.addAttribute("icon", "error");
	        model.addAttribute("loc", "/member/joinFrm");
	        return "common/msg";
	    }
	    int result = memberService.insertMember(m);
	    model.addAttribute("title", "회원가입 완료");
	    model.addAttribute("text", "가입을 환영합니다");
	    model.addAttribute("icon", "success");
	    model.addAttribute("loc", "/member/loginFrm");	    
	    session.removeAttribute("emailVerified");
	    return "common/msg";	    	
	}// join
	
	// ajax 이메일 조회
	@ResponseBody
	@GetMapping(value="ajaxCheckEmail")
	public int ajaxCheckEmail(String memberEmail) {
		Member m = memberService.selectOneMember(memberEmail);
		if(m != null) {
			return 1;
		}else {
			return 0;
		}
	}// ajaxCheckEmail
	
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}// logout
	
	@GetMapping(value="/mypage")
	public String mypage(@SessionAttribute(required = false) Member member, Model model) {
		if(member == null) {
			model.addAttribute("title", "로그인 확인");
			model.addAttribute("text", "로그인 후 이용 가능합니다.");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/member/loginFrm");
			return "common/msg";
		}else {
			return "member/mypage";
		}
	}// mypage
	
	@PostMapping(value="/update")
	public String update(Member m, HttpSession session) {
		int result = memberService.updateMember(m);
		if(result > 0) {
			Member member = (Member)session.getAttribute("member");
			member.setMemberNickname(m.getMemberNickname());
			member.setMemberPw(m.getMemberPw());
		}
		return "redirect:/member/mypage";
	}// update
	
	@GetMapping(value="delete")
	public String delete(@SessionAttribute Member member, Model model, HttpSession session) {
		int memberNo = member.getMemberNo();
		int result = memberService.deleteMember(memberNo);
		model.addAttribute("title", "회원 탈퇴 완료");
		model.addAttribute("text", "회원탈퇴가 정상적으로 완료되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/member/logout");
		return "common/msg";
	}// delete
	
}// MemberController Class
