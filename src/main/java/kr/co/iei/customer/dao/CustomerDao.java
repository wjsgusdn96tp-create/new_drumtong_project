package kr.co.iei.customer.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.customer.vo.Customer;
import kr.co.iei.customer.vo.CustomerComment;
import kr.co.iei.customer.vo.CustomerServiceFile;
import kr.co.iei.member.model.vo.Member;

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
	
	int insertCustomerComment(CustomerComment cc);
	
	List<CustomerComment> selectCustomerCommentList(int customerNo);
	
	int deleteComment(int commentNo);
	
	List<Member> selectAllMember(); //멤버 객체 불러오기용
	
	CustomerComment selectOneComment(int commentNo);
	
	int updateComment(CustomerComment cc);
	
	int updateStarRating(Customer customer);
}
