package kr.co.iei.customer.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.customer.vo.Customer;
import kr.co.iei.customer.vo.CustomerServiceFile;

@Mapper
public interface CustomerDao {

	List selectCustomerList(HashMap<String, Object> param);
	int selectCustomerTotalCount(HashMap<String, Object> param);
	
	Customer selectOneCustomer(int customerNo);
	
	int insertCustomer(Customer customer);
	
	int getCustomerNo();
	
	int insertCustomerFile(CustomerServiceFile customerServiceFile);
	
	List<CustomerServiceFile> selectCustomerFiles(int customerNo);
	int deleteCustomerFiles(int customerNo);
	
	int deleteCustomer(int customerNo);
	
}
