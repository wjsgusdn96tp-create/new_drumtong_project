package kr.co.iei.customer.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.customer.dao.CustomerDao;
import kr.co.iei.customer.vo.Customer;
import kr.co.iei.customer.vo.CustomerListData;
import kr.co.iei.customer.vo.CustomerNavi;
import kr.co.iei.customer.vo.CustomerServiceFile;
import kr.co.iei.member.model.vo.Member;


@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	public CustomerListData selectCustomerList(int reqPage, String sort, Member member, String category) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
					
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("start",start);
		param.put("end", end);
		param.put("sort", sort);
		if(member != null) {
			param.put("memberGrade", member.getMemberGrade());
			param.put("memberNickname", member.getMemberNickname());
		}
	    if (category != null) {
	        param.put("category", category);
	    }
		
		List list = customerDao.selectCustomerList(param);
		
		int totalCount = customerDao.selectCustomerTotalCount(param);
		int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
		int pageNaviSize = 5;
		int startNo = ((reqPage -1) / pageNaviSize) * pageNaviSize + 1;
		int endNo = startNo + (pageNaviSize - 1);
		if(endNo > totalPage) {
			endNo = totalPage;
		}
		boolean prev = startNo > 1;
	    boolean next = endNo < totalPage;
	    
	    CustomerNavi customerNavi = new CustomerNavi();
	    customerNavi.setEndNo(endNo);
	    customerNavi.setNext(next);
	    customerNavi.setPageNaviSize(pageNaviSize);
	    customerNavi.setPrev(prev);
	    customerNavi.setReqPage(reqPage);
	    customerNavi.setStartNo(startNo);
	    customerNavi.setTotalPage(totalPage);
	    
	    CustomerListData cld = new CustomerListData(list, customerNavi);
		return cld;
	}

	
	public Customer selectOneCustomer(int customerNo) {
		Customer c = customerDao.selectOneCustomer(customerNo);
		
		List<CustomerServiceFile> fileList = customerDao.selectCustomerFiles(customerNo);
		c.setFileList(fileList);
		return c;
	}
	
	@Transactional
	public int insertCustomer(Customer customer, List<CustomerServiceFile> fileList) {
		int newCustomerNo = customerDao.getCustomerNo();
		customer.setCustomerNo(newCustomerNo);
		int result = customerDao.insertCustomer(customer);
		for(CustomerServiceFile customerServiceFile : fileList) {
			customerServiceFile.setCustomerNo(newCustomerNo);
			result += customerDao.insertCustomerFile(customerServiceFile);
		}
		return result;
	}

	@Transactional
	public int deleteCustomer(int customerNo) {
		List<CustomerServiceFile> fileList = customerDao.selectCustomerFiles(customerNo);
		int result = customerDao.deleteCustomer(customerNo);
		
		return result;
	}
	
	
}










