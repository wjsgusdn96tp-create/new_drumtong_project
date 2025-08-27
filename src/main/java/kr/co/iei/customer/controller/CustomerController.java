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
	public String goodjobList(int reqPage, String sort, String category, Model model) {
		CustomerListData cld = customerService.selectGjList(reqPage, sort, category);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		model.addAttribute("category", category);
		return "customer/listGj";
	}
	
	@GetMapping(value="/complain")
	public String complainList(int reqPage, String sort, Model model) {
		CustomerListData cld = customerService.selectComplainList(reqPage, sort);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/listComplain";
	}
	
	@GetMapping(value="/opinion")
	public String opinionList(int reqPage, String sort, Model model) {
		CustomerListData cld = customerService.selectOpinionList(reqPage, sort);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/listOpinion";

	}
	
	
	
}
