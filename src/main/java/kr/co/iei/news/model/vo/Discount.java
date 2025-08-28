package kr.co.iei.news.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value="discount")
public class Discount {
	private int discountNo;
	private int newsNo;
	private int productNo;
	private int discountPercent;
	private int discountPrice;
}
