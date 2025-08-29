package kr.co.iei.product.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.product.vo.Product;

@Mapper
public interface ProductDao {

	int productInsertDrink(Product p);

	int productBestTblInsert(Product p, int referencesProductNo);

}
