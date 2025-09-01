package kr.co.iei.customer.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="customer")
public class Customer {

	private int customerNo;
	private String customerNickname;
	private String customerCategory;
	private String customerTitle;
	private String customerContent;
	private String customerDate;
	private int customerStar;
	private List<CustomerServiceFile> fileList;
	
}
