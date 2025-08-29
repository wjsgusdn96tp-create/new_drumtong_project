package kr.co.iei.customer.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.customer.vo.Customer;

@Mapper
public interface CustomerDao {

	List selectCustomerList(HashMap<String, Object> param);
	int selectCustomerTotalCount(HashMap<String, Object> param);

	List selectGjList(HashMap<String, Object> param);
	int selectGjTotalCount(HashMap<String, Object> param);

	List selectComplainList(HashMap<String, Object> param);
	int selectComplainTotalCount(HashMap<String, Object> param);

	List selectOpinionList(HashMap<String, Object> param);
	int selectOpinionTotalCount(HashMap<String, Object> param);
	Customer selectOneCustomer(int customerNo);
}
