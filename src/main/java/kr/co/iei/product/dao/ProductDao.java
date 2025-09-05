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

	int productGoodsUpdate(int productNo, int productPrice, String productName, String productContentPresent);

	int productGoodsUpdateBest(String productName, int productBestNo);

	int productUpdate(int productNo, int productPrice, String productName);

	int productUpdateBest(String productName, int productBestNo);

	int insertProductLike(HashMap<String, Object> param);

	int deleteProductLike(HashMap<String, Object> param);

	int selectProductLikeCount(int productNo);

	List dessertList(HashMap<String, Object> param);

	List selectDessertList(HashMap<String, Object> param);

	List dessertListPriceDate(HashMap<String, Object> param);

	List goodsList(HashMap<String, Object> param);

	List selectGoodsList(HashMap<String, Object> param);

	List goodsListPriceDate(HashMap<String, Object> param);

	
	List IceCoffeList(HashMap<String, Object> param);

	List IceTeaList(HashMap<String, Object> param);

	List IceOtherDrinkList(HashMap<String, Object> param);

	List HotCoffeList(HashMap<String, Object> param);

	List HotTeaList(HashMap<String, Object> param);
	
	//
	List selectIceCoffeList(HashMap<String, Object> param);

	List selectIceTeaList(HashMap<String, Object> param);

	List selectIceOtherDrinkList(HashMap<String, Object> param);

	List selectHotCoffeList(HashMap<String, Object> param);

	List selectHotTeaList(HashMap<String, Object> param);

	//
	List IceCoffeListPriceDate(HashMap<String, Object> param);

	List IceTeaListPriceDate(HashMap<String, Object> param);

	List IceOtherDrinkListPriceDate(HashMap<String, Object> param);

	List HotCoffeListPriceDate(HashMap<String, Object> param);

	List HotTeaListPriceDate(HashMap<String, Object> param);

	int selectDessertTotalCount();

	int selectGoodsTotalCount();
	
	int selectIceCoffeTotalCount();
	
	int selectIceTeaTotalCount();
	
	int selectIceOtherDrinkTotalCount();
	
	int selectHotCoffeTotalCount();
	
	int selectHotTeaTotalCount();
	

}
