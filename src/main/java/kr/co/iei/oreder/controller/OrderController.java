package kr.co.iei.oreder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.iei.order.service.OrderService;
import kr.co.iei.order.vo.DetailsTbl;
import kr.co.iei.order.vo.ShopTbl;
import kr.co.iei.product.vo.Product;

@Controller
@RequestMapping(value="/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping("/OrderMap")
	public String orderMapPage(Model model) {

	    int reqPage = 1; 

	    List list = orderService.shopList(reqPage);

	    model.addAttribute("reqPage", reqPage);
	    model.addAttribute("list", list);

	    return "order/OrderMap";
	}
	
	@GetMapping (value="/Orderoption")
	public String orderListpage(int productNo,Model model) {
		Product p = orderService.option(productNo);
		
		//productNo = DB에 물품번호 기준으로 담겨있는 정보들 이름,이미지,가격 등등
		
		model.addAttribute("p", p);
		
		return "order/Orderoption";
	}
	
	/*
	@GetMapping("/Orderoption")
	public String orderListpage(int productNo, String shopName, int reqPage, Model model) {
	    Product p = orderService.option(productNo);
	    model.addAttribute("p", p);
	    
	    model.addAttribute("shopName", shopName); // 지점명 전달
	    model.addAttribute("reqPage", reqPage);   // 페이지 번호 전달
	    return "order/Orderoption";
	}
	*/

	@PostMapping (value= "/DrumtongCart")
	public String cartPage(Model model,DetailsTbl dt) {
		
		int result = orderService.insertCart(dt);
		
		if(result == 1) {
			System.out.println("성공");
		}else {
			System.out.println("실패");
		}
		
		return "order/DrumtongCart";
	}
	
	
}
