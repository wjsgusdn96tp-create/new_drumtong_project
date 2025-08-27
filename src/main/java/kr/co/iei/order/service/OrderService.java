package kr.co.iei.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.order.dao.OrderDao;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;

	
	public List shopList() {
		List list  = orderDao.shopList();
		return list;
	}
}
