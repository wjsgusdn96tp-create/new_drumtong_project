package kr.co.iei.membershiprecode.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.membershiprecode.model.service.MemberShipRecodeService;
import kr.co.iei.membershiprecode.model.vo.MemberShipRecode;

@Controller
@RequestMapping(value="/membershipRecode")
public class MemberShipRecodeController {
	@Autowired
	private MemberShipRecodeService membershiprecodeService;
	
	@PostMapping(value="/insert")
	@ResponseBody
	public String insertRecode(MemberShipRecode recode, HttpSession session) {
	    if (session.getAttribute("member") == null) {
	        return "member/loginFrm";
	    }
		int result = membershiprecodeService.insertRecode(recode);
		return "redirect:/";
	}
	
	@GetMapping("/membership")
	public String membershipPage(HttpSession session, Model model) {
	    boolean loggedIn = (session.getAttribute("member") != null);
	    model.addAttribute("loggedIn", loggedIn);
	    return "membership/membershipPage";
	}
	
	// 비 로그인시 로그인 요구 메세지페이지로 이동
	@GetMapping("/requireLogin")
	public String requireLogin(Model model) {
	    model.addAttribute("title", "로그인이 필요합니다");
	    model.addAttribute("text", "로그인 후 이용바랍니다.");
	    model.addAttribute("icon", "error");
	    model.addAttribute("loc", "/member/loginFrm");
	    return "common/msg";
	}
}
