package kr.co.iei.oreder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.order.service.OrderService;
import kr.co.iei.order.vo.CartItem;
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
	public String orderListpage(int productNo,Model model,String shopName,
			@SessionAttribute(required = false) Member member) {
		
		Product p = orderService.option(productNo);
		
		//productNo = DB에 물품번호 기준으로 담겨있는 정보들 이름,이미지,가격 등등
		
		model.addAttribute("p", p);
		model.addAttribute("shopName", shopName);
		//로그인 하면 회원번호 나오게하기
		 if(member != null) {
	        model.addAttribute("memberNo", member.getMemberNo());
	        }
		 
		return "order/Orderoption";
	}
	
	
	@PostMapping (value= "/DrumtongCart")
	public String cartPage(Model model,CartItem ct,
			@SessionAttribute(required = false) Member member
			) {
		//오면 강사님한테 꼭 물어보기 할인에 대해서!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		//세션에서 가져온 회원번호 세팅하기 ct에 저장 		잊지말자 
		ct.setMemberNo(member.getMemberNo());
		 
		int result = orderService.insertCart(ct);
		
		model.addAttribute("ct",ct);
		
		return "order/DrumtongCart";
	}
	
}
