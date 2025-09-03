package kr.co.iei.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.order.vo.CartItem;
import kr.co.iei.order.vo.DetailsTbl;
import kr.co.iei.order.vo.OrderTbl;
import kr.co.iei.order.vo.ShopTbl;
import kr.co.iei.product.vo.Product;

@Mapper
public interface OrderDao {


	List shopList(int reqPage);

	Product option(int productNo);
	
	ShopTbl getShop(String shopName);

	int insertCart(CartItem ct);
	
	List selectCartList(int num, int productNo, String shopName);

	int insertOrderTbl(OrderTbl otb);




}

