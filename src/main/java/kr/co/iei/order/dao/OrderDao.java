package kr.co.iei.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.product.vo.Product;

@Mapper
public interface OrderDao {


	List shopList();



	Product option(int productNo);

}

