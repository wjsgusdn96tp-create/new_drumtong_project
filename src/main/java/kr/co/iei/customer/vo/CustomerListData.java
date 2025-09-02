package kr.co.iei.customer.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerListData {
	private List list;
	private CustomerNavi customerNavi;
	private int delResult;
}
