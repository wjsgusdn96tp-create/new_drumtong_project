package kr.co.iei.product.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import kr.co.iei.product.vo.Product;

@Mapper
public interface ProductDao {

	int productInsert(Product p);

	Product productInsertSearch(String productName);

	int productBestTblInsert(Product p);

	int productInsertDelete(Product p);

	int selectProductTotalCount();

	List selectProductList(HashMap<String, Object> param);

	Product productBestSelect(int productNo);

	int productBestDelete(int productNo);

	int productDelete(int productNo);

	List bestProductList();

	List ProductListDessert(HashMap<String, Object> param);

	List productListPriceDate(HashMap<String, Object> param);

	List notBestProductList();

	List allproductList(HashMap<String, Object> param);

	Product searchProductUpdate(int productNo);

}
