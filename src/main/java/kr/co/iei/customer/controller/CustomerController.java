package kr.co.iei.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.iei.customer.service.CustomerService;
import kr.co.iei.customer.vo.Customer;
import kr.co.iei.customer.vo.CustomerListData;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value="/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping(value="/list")
	public String customerList(int reqPage, String sort, Model model, @SessionAttribute(required = false) Member member) {
		CustomerListData cld = customerService.selectCustomerList(reqPage, sort, member);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/list";
	}
	
	@GetMapping(value="/goodjob")
	public String goodjobList(int reqPage, String sort, String category, Model model, @SessionAttribute(required = false) Member member) {
		CustomerListData cld = customerService.selectGjList(reqPage, sort, member);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/listGj";
	}
	
	@GetMapping(value="/complain")
	public String complainList(int reqPage, String sort, Model model, @SessionAttribute(required = false) Member member) {
		CustomerListData cld = customerService.selectComplainList(reqPage, sort, member);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/listComplain";
	}
	
	@GetMapping(value="/opinion")
	public String opinionList(int reqPage, String sort, Model model, @SessionAttribute(required = false) Member member) {
		CustomerListData cld = customerService.selectOpinionList(reqPage, sort, member);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/listOpinion";

	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		return "customer/writeFrm";
	}
	
	@GetMapping(value="/view")
	public String CustomerView(int customerNo, @SessionAttribute(required = false) Member member, Model model) {
		Customer c = customerService.selectOneCustomer(customerNo);
			
		
		model.addAttribute("c", c);
		return "customer/view";
	}
}
