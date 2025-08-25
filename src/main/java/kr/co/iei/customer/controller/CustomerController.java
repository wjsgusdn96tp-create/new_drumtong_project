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
	public String customerList(int reqPage, Model model) {
		CustomerListData cld = customerService.selectCustomerList(reqPage);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		return "customer/list";
	}
}
