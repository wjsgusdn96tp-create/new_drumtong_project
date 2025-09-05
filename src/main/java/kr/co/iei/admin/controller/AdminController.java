package kr.co.iei.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.order.service.OrderService;
import kr.co.iei.order.vo.Chart;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrderService orderService;
	
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
	
	
	@GetMapping(value="/adminChart")
	public String adminChart(Model model) {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String thisYear = dateFormat.format(currentDate);
		
		List<Chart> chartListPay = orderService.selectOrderPay();
		for(int i=0 ; i<5; i++) {
			for(int j=0 ; j<chartListPay.size(); j++) {
				int year = chartListPay.get(j).getYear();
				if(Integer.parseInt(thisYear)-i == year){
					model.addAttribute("Pvalue"+(i+1), chartListPay.get(i).getSumPay());				
				} else {
					model.addAttribute("Pvalue"+(i+1), 0);
				}
			}
		}
		List<Chart> chartListCount = orderService.selectOrderCount();
		for(int i=0 ; i<5; i++) {
			for(int j=0 ; j<chartListCount.size(); j++) {
				int year = chartListCount.get(j).getYear();
				if(Integer.parseInt(thisYear)-i == year){
					model.addAttribute("Cvalue"+(i+1), chartListCount.get(i).getSumCount());				
				} else {
					model.addAttribute("Cvalue"+(i+1), 0);
				}
			}
		}
		List<Chart> chartListShop = orderService.selectOrderShop();
		for(int i=0 ; i<chartListShop.size(); i++) {
			System.out.println(chartListShop.get(i));
			model.addAttribute("Shop"+(i+1), chartListShop.get(i).getShopName());
			model.addAttribute("SPvalue"+(i+1), chartListShop.get(i).getSumPay());
			model.addAttribute("CPvalue"+(i+1), chartListShop.get(i).getSumCount());
		}
		
		return "/admin/adminChart";
	}
	
}// AdminController Class
