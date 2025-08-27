package kr.co.iei.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.customer.service.CustomerService;
import kr.co.iei.customer.vo.CustomerListData;

@Controller
@RequestMapping(value="/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping(value="/list")
	public String customerList(int reqPage, String sort, Model model) {
		CustomerListData cld = customerService.selectCustomerList(reqPage, sort);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/list";
	}
	
	@GetMapping(value="/goodjob")
	public String goodjobList(int reqPage, Model model) {
		CustomerListData cld = customerService.selectGjList(reqPage);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		return "customer/listGj";
	}
	
	@GetMapping(value="/complain")
	public String complainList(int reqPage, Model model) {
		CustomerListData cld = customerService.selectComplainList(reqPage);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		return "customer/listComplain";
	}
	
	@GetMapping(value="/opinion")
	public String opinionList(int reqPage, Model model) {
		CustomerListData cld = customerService.selectOpinionList(reqPage);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		return "customer/listOpinion";

	}
	
	
	
}
