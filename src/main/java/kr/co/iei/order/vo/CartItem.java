package kr.co.iei.order.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value = "cart")
public class CartItem {
	private int cartNo;
	private String cupChoice;
	private String cupSize;
	private int cupShot;
	private int cupCream;
	private int heat;
	private int count;
	private int productNo;
	private int memberNo;
	private String shopName;
	private String discountName;
	private int discountPrice;
}
