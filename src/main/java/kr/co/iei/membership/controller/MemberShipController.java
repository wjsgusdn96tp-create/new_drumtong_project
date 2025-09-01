package kr.co.iei.membership.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.membership.model.service.MemberShipService;
import kr.co.iei.membership.model.vo.MemberShip;

@Controller
@RequestMapping(value = "/membership")
public class MemberShipController {
	@Autowired
	private MemberShipService membershipService;
	
    @GetMapping("/membershipPage")
    public String membershipPage(Model model, MemberShip ms) {
        List<MemberShip> list = membershipService.selectAllMembership(ms);
        System.out.println(list);
        model.addAttribute("list", list);
        return "membership/membershipPage";
    }
	
}
