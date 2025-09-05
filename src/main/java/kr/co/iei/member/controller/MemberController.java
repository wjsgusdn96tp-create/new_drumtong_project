package kr.co.iei.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	    String email = (m.getMemberEmail() != null) ? m.getMemberEmail().trim() : ""; // 공백 확인
	    String pw    = (m.getMemberPw() != null)    ? m.getMemberPw().trim()    : ""; // 공백 확인
	    // 입력 공백 확인
	    if (email.isEmpty() || pw.isEmpty()) {
	        model.addAttribute("title", "로그인 실패");
	        model.addAttribute("text", "이메일과 비밀번호를 모두 입력해주세요.");
	        model.addAttribute("icon", "error");
	        model.addAttribute("loc", "/member/loginFrm");
	        return "common/msg";
	    }
	    // 이메일 대조
	    if (member == null) {
	        model.addAttribute("title", "로그인 실패");
	        model.addAttribute("text", "존재하지 않는 이메일입니다.");
	        model.addAttribute("icon", "error");
	        model.addAttribute("loc", "/member/loginFrm");
	        return "common/msg";
	    }

	    // 입력한 비밀번호와 DB 비밀번호 직접 비교
	    if (!pw.equals(member.getMemberPw())) {
	        model.addAttribute("title", "로그인 실패");
	        model.addAttribute("text", "비밀번호가 일치하지 않습니다.");
	        model.addAttribute("icon", "error");
	        model.addAttribute("loc", "/member/loginFrm");
	        return "common/msg";
	    }
	    session.setAttribute("member", member);
		return "redirect:/";
	}// login
	
	@GetMapping(value="/joinFrm")
	public String joinFrm() {
		return "member/joinFrm";
	}// joinFrm
	
	@PostMapping(value="/join")
	public String join(Member m, Model model, HttpSession session) {
		// 필수값 (500 에러 방지)
	    if (m.getMemberEmail()==null || m.getMemberEmail().isEmpty()
	        || m.getMemberPw()==null || m.getMemberPw().isEmpty()
	        || m.getMemberNickname()==null || m.getMemberNickname().isEmpty()) {
	    	model.addAttribute("title", "회원가입 실패");
	    	model.addAttribute("text", "필수정보 ! 이메일/비밀번호/닉네임을 모두 입력하세요.");
	    	model.addAttribute("icon", "error");
	    	model.addAttribute("loc", "/member/joinFrm");
	    	return "common/msg";	    	
	    }
	        
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
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		model.addAttribute("title", "로그아웃");
		model.addAttribute("text", "로그아웃 되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/");
		return "common/msg";
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
	public String update(Member m, HttpSession session, Model model) {
		int result = memberService.updateMember(m);
		if(result > 0) {
			Member member = (Member)session.getAttribute("member");
			member.setMemberPw(m.getMemberPw());
	        // 성공 알림
	        model.addAttribute("title", "비밀번호 변경 완료");
	        model.addAttribute("text", "비밀번호가 성공적으로 변경되었습니다.");
	        model.addAttribute("icon", "success");
	        model.addAttribute("loc", "/"); // 성공 후 이동할 페이지
		}else {
	        // 실패 알림
	        model.addAttribute("title", "비밀번호 변경 실패");
	        model.addAttribute("text", "비밀번호 변경 중 오류가 발생했습니다.");
	        model.addAttribute("icon", "error");
	        model.addAttribute("loc", "/member/mypage");
		}
		return "common/msg";
	}// update
	
	@GetMapping(value="/delete")
	public String delete(@SessionAttribute Member member, Model model, HttpSession session) {
		int memberNo = member.getMemberNo();
		int result = memberService.deleteMember(memberNo);
		model.addAttribute("title", "회원 탈퇴 완료");
		model.addAttribute("text", "회원탈퇴가 정상적으로 완료되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/");
		return "common/msg";
	}// delete
	
    // passwordReset form
    @GetMapping("/passwordResetFrm")
    public String passwordResetFrm() {
        return "member/passwordReset";
    }
    // 비밀번호 변경 실행
    @PostMapping("/passwordReset")
    public String passwordReset(@RequestParam String memberEmail, @RequestParam String newPassword, Model model) {
        int updated = memberService.updatePasswordByEmail(memberEmail, newPassword);
        if (updated > 0) {
            model.addAttribute("title", "비밀번호가 변경되었습니다.");
            model.addAttribute("text", "로그인페이지로 이동합니다.");
            model.addAttribute("icon", "success");
            model.addAttribute("loc", "/member/loginFrm");
            return "common/msg";
        } else {
            model.addAttribute("error", "해당 이메일을 찾을 수 없습니다.");
        }
        return "member/passwordReset";
    }
}// MemberController Class
