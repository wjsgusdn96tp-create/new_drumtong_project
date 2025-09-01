package kr.co.iei.oreder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.order.service.OrderService;
import kr.co.iei.order.vo.CartItem;
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
		
		//매장이름 받아오기
		ShopTbl shop = orderService.getShop(shopName); 
		
		//productNo = DB에 물품번호 기준으로 담겨있는 정보들 이름,이미지,가격 등등
		
		model.addAttribute("p", p);
		model.addAttribute("shop", shop);
		
		//로그인 하면 회원번호 나오게하기
		 if(member != null) {
	        model.addAttribute("memberNo", member.getMemberNo());
	        }
		 
		return "order/Orderoption";
	}
	
	
	
	//에이잭스에서 db로 추가만 하는 로직들
	@PostMapping("/DrumtongCart")
	@ResponseBody
	public int insertCart(CartItem ct, @SessionAttribute(required = false) Member member) {
		
		//세션에서 가져온 회원번호 세팅하기 잊지말자 

		ct.setMemberNo(member.getMemberNo());
	    
		int result = orderService.insertCart(ct); 
		
	    return result; 
	}
	
	
	// 장바구니 페이지 열기
	@GetMapping("/DrumtongCart")
	public String cartPage(Model model,int productNo,String shopName,String productName,
			@SessionAttribute(required = false) Member member) {
	    
		int num = member.getMemberNo();
		
		List list = orderService.selectCartList(num,productNo,shopName,productName);
		
	    model.addAttribute("list", list);
	    return "order/DrumtongCart";
	}
}
