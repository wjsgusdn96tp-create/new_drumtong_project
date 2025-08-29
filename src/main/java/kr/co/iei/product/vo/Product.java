package kr.co.iei.product.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value="product")
public class Product {
	private int productNo;
	private String productName;
	private int productPrice;
	private String productTitle;
	private String productTilteTemp;
	private String productTitleDetail;
	private String productContentPresent;
	private int productContentKcal;
	private int productContentShot;
	private int productContentSugar;
	private int productContentNatrium;
	private int productContentProtein;
	private String productImg;  
	//좋아요 기능 관련
	/*
	private int isLike;
	private int likeCount;
	*/
}
