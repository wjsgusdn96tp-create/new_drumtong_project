package kr.co.iei.oreder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.order.service.OrderService;
import kr.co.iei.order.vo.DetailsTbl;
//import kr.co.iei.order.vo.DetailsTbl;
import kr.co.iei.order.vo.ShopTbl;
import kr.co.iei.product.vo.Product;

@Controller
@RequestMapping(value="/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping (value="/OrderMap")
	public String orderMapPage(Model model) {
		
		List list = orderService.shopList();
		
		model.addAttribute("list", list);
		
		return "order/OrderMap";
	}
	
	@GetMapping (value="/Orderoption")
	public String orderListpage(int productNo,Model model) {
		Product p = orderService.option(productNo);
		
		model.addAttribute("p", p);
		
		return "order/Orderoption";
	}

	
}
