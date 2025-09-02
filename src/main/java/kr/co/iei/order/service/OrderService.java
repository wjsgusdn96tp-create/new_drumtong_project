package kr.co.iei.order.service;

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


	public List selectCartList(int num, int productNo, String shopName, String productName) {
		List list = orderDao.selectCartList(num,productNo,shopName,productName);
		
		return list;
	}





	public OrderTbl insertOtb(String shopName, int num) {
		OrderTbl otb = orderDao.insertotb(shopName, num);
		
		return otb;
	}





	



	
}
