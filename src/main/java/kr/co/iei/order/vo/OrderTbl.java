package kr.co.iei.order.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value = "order")
public class OrderTbl {
	private int orderNo;
	private int memberNo;
	private String shopName;
	private Date enrollDate;
}
