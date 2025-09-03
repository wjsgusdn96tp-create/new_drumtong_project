package kr.co.iei.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value="/adminPage")
	public String allMember(Model model) {
		List list = memberService.selectAllMember();
		model.addAttribute("list", list);
		return "admin/adminPage";
	}// allMember
	
	@GetMapping(value="/changeMemberGrade")
	public String changeGrade(Member m) {
		int result = memberService.changeMemberGrade(m);
		return "redirect:/admin/adminPage";
	}// changeMembershipGrade
	
	@GetMapping(value="/checkedChangeGrade")
	public String checkedChangeGrade(String no, String grade) {
		boolean result = memberService.checkedChangeGrade(no,grade);
		return "redirect:/admin/adminPage";
	}
}// AdminController Class
