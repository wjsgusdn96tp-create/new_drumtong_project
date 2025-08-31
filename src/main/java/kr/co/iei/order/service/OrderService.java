package kr.co.iei.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.order.dao.OrderDao;
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
}
