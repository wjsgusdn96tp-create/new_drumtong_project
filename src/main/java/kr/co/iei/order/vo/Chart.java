package kr.co.iei.order.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("chart")
public class Chart {
	private int year;
	private int sumPay;
	private int sumCount;
	private String shopName;
}
