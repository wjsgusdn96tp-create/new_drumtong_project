package kr.co.iei.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {

	List shoplist();

}

