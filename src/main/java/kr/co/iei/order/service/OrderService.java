package kr.co.iei.order.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.order.dao.OrderDao;
import kr.co.iei.order.vo.CartItem;
import kr.co.iei.order.vo.DetailsTbl;
import kr.co.iei.order.vo.OrderTbl;
import kr.co.iei.order.vo.ShopTbl;
import kr.co.iei.product.vo.Product;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;

	
	public List shopList(int reqPage) {
		List list  = orderDao.shopList(reqPage);
		return list;
	}


	public Product option(int productNo) {
		Product p = orderDao.option(productNo);
		
		return p;
	}

	public ShopTbl getShop(String shopName) {
		ShopTbl shop = orderDao.getShop(shopName); 
		
		return shop;
	}

	@Transactional
	public int insertCart(CartItem ct) {
		int result = orderDao.insertCart(ct);
		
		return result;
	}

	
	public List selectCartList(int memberNo,String shopName) {
		
		HashMap<String, Object> param = new HashMap<String,Object>();
		
		param.put("memberNo", memberNo);
		param.put("shopName", shopName);
		
		List list = orderDao.selectCartList(param);
		
		return list;
	}

	@Transactional
	public int insertOrderTbl(OrderTbl otb) {
		
		int orderNo  = orderDao.getorderNo();
		otb.setOrderNo(orderNo);
		
		int memberNo = otb.getMemberNo();
		String shopName = otb.getShopName();
		
		//orderVo 에서 받아온 값을 해쉬맵에 대입
		HashMap<String, Object> param = new HashMap<String,Object>();
		
		param.put("memberNo", memberNo);
		param.put("shopName", shopName);
		
		
		
		//대입한 해쉬맵을 list에 대입
		int orderTbl = orderDao.insertOrderTbl(otb);
		
		
		List<CartItem> list = orderDao.selectCartList(param);
		
		
		for(int i=0 ; i < list.size() ; i++) {
			
		 CartItem o = list.get(i);
		
		 DetailsTbl dtl = new DetailsTbl();
		 
		 
		 dtl.setCupChoice(o.getCupChoice());
		 dtl.setCupSize(o.getCupSize());
		 dtl.setCupShot(o.getCupShot());
		 dtl.setCupCream(o.getCupCream());
		 dtl.setHeat(o.getHeat());
		 dtl.setCount(o.getCount());
		 dtl.setProductNo(o.getProductNo());
		 dtl.setOrderNo(orderNo);
		 dtl.setShopName(shopName);
		 dtl.setPay(o.getPay());
		 dtl.setDiscountName(o.getDiscountName());
		 dtl.setDiscountPrice(o.getDiscountPrice());
		 dtl.setProductName(o.getProductName());
		 
		 System.out.println(dtl.getOrderNo());
		 System.out.println(dtl.getProductNo());
		 
		 int resultDt = orderDao.insertDetailsTbl(dtl);
	
		}
		
		int result = orderDao.deleteCart(param);
		
		return result;
	}

	
	@Transactional
	public int cartdel(int cartNo) {
		int result = orderDao.cartdel(cartNo);
		
		return result;
	}


	
	










	



	
}
