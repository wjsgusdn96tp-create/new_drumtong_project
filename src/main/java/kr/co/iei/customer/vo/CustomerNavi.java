package kr.co.iei.customer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerNavi {
	private int totalPage; 
	private int pageNaviSize;
	private int reqPage;
    private int startNo;
    private int endNo;
    private boolean prev;
    private boolean next;

}
