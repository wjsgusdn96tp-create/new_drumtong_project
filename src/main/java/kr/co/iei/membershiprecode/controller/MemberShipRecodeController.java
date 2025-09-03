package kr.co.iei.membershiprecode.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.membershiprecode.model.service.MemberShipRecodeService;
import kr.co.iei.membershiprecode.model.vo.MemberShipRecode;

@Controller
@RequestMapping(value="/membershipRecode")
public class MemberShipRecodeController {
	@Autowired
	private MemberShipRecodeService membershiprecodeService;
	
	@PostMapping(value="/insert")
	public String insertRecode(MemberShipRecode recode) {
		int result = membershiprecodeService.insertRecode(recode);
		return "redirect:/";
	}
}
