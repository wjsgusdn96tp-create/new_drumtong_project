package kr.co.iei.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.product.dao.ProductDao;
import kr.co.iei.product.vo.Product;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Transactional
	public int productInsertDrink(Product p, String productBestTbl) {
		int referencesProductNo =0;
		if(productBestTbl.equals("productBest")) {
			referencesProductNo ++;
			int result = productDao.productInsertDrink(p);
			if(result == 1) {
				int resultBest = productDao.productBestTblInsert(p, referencesProductNo);
				if(resultBest > 0) {
					return 2;
				}else {
					return 1;
				}
			}return result;/*0*/
			
		}else {
			referencesProductNo ++;
			int result = productDao.productInsertDrink(p);
			return result;/*1*/
		}
		
		/*
		if(productBestTbl.equals("productBest")) {
			referencesProductNo ++;
			int result = productDao.productInsertDrink(p);
			if(result > 0) {
				int resultBest = productDao.productBestTblInsert(p, referencesProductNo);
				if(resultBest>0){
				return result;				
				}else {
					return 
				}
				/*else {
				if(result > 0) {
					/*productBest 등록 x
					return -1;
				}else {
					/*product 등록 x
					return -2;
				}
			}
		}else{
			referencesProductNo ++;
			int result = productDao.productInsertDrink(p);
			return result;
		}*/
		
	}
}
