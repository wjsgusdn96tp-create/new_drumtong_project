package kr.co.iei.customer.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.customer.dao.CustomerDao;
import kr.co.iei.customer.vo.CustomerListData;
import kr.co.iei.customer.vo.CustomerNavi;


@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	public CustomerListData selectCustomerList(int reqPage, String sort) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
					
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("start",start);
		param.put("end", end);
		param.put("sort", sort);
		List list = customerDao.selectCustomerList(param);
		
		int totalCount = customerDao.selectCustomerTotalCount();
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

	public CustomerListData selectGjList(int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("start",start);
		param.put("end", end);
		List list = customerDao.selectGjList(param);
		
		int totalCount = customerDao.selectGjTotalCount();
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

	public CustomerListData selectComplainList(int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("start",start);
		param.put("end", end);
		List list = customerDao.selectComplainList(param);
		
		int totalCount = customerDao.selectComplainTotalCount();
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

	public CustomerListData selectOpinionList(int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("start",start);
		param.put("end", end);
		List list = customerDao.selectOpinionList(param);
		
		int totalCount = customerDao.selectOpinionTotalCount();
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
	
}
