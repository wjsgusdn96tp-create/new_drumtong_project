package kr.co.iei.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.order.vo.CartItem;
import kr.co.iei.order.vo.DetailsTbl;
import kr.co.iei.product.vo.Product;

@Mapper
public interface OrderDao {


	List shopList(int reqPage);

	Product option(int productNo);

	int insertCart(CartItem ct);



}

