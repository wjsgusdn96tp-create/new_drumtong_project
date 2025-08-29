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
		/*int referencesProductNo =0;*/
		if(productBestTbl.equals("productBest")) {
			/*referencesProductNo ++;*/
			int result = productDao.productInsertDrink(p);
			if(result > 0) {
				/*p.setProductNo(referencesProductNo);*/
				String productName= p.getProductName();
				Product productSearch = productDao.productInsertSearch(productName);
				int resultBest = productDao.productBestTblInsert(productSearch);
				if(resultBest > 0) {
					return resultBest;/*1*/
				}else {
					int productInsertCancel = productDao.productInsertDelete(p);
					if(productInsertCancel >0) {
						return resultBest;/*0*/
					}else {
						return 10;/*일반상품등록, 대표상품 등록실패*/
					}
				}
			}else{
				return result;
			}
		}else {
			/*referencesProductNo ++;*/
			int result = productDao.productInsertDrink(p);
			return result;/*1*/
		}
	}
	
	
}
