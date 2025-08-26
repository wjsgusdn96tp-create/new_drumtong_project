package kr.co.iei.oreder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/order")
public class OrderController {
	
	@GetMapping (value="/OrderMap")
	public String writeFrm() {
		
		return "order/OrderMap";
	}
}
