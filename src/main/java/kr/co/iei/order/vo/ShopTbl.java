package kr.co.iei.order.vo;

import org.apache.ibatis.type.Alias;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value = "shop")
public class ShopTbl {
	private String shopName;
	private String shopAddress;
	private String shopLatitude;
	private String shopLongitude;
	
}
