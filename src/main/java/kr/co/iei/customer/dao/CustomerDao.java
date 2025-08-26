package kr.co.iei.customer.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerDao {

	List selectCustomerList(HashMap<String, Object> param);

	int selectCustomerTotalCount();

	List selectGjList(HashMap<String, Object> param);

	List selectComplainList(HashMap<String, Object> param);

	List selectOpinionList(HashMap<String, Object> param);

	int selectGjTotalCount();

	int selectComplainTotalCount();

	int selectOpinionTotalCount();
}
