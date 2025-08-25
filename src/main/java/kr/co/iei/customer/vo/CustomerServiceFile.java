package kr.co.iei.customer.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="customerServiceFile")
public class CustomerServiceFile {
	
	private int customerFileNo;
	private int customerNo;
	private String fileName;
	private String filePath;
}
