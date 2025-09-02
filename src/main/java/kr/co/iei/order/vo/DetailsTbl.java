
package kr.co.iei.order.vo;



import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value = "details")


public class DetailsTbl {
	private int detailsNo;
	private String cupChoice;
	private String cupSize;
	private int cupShot;
	private int cupCream;
	private int heat;
	private int count;
	private int productNo;
	private int orderNo;
	private int pay;
	private String discountName;
	private int discountPrice; 
	private Date enrollDate;
	private String productName;
}
