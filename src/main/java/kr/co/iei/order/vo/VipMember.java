package kr.co.iei.order.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data 
@Alias(value = "vip")
public class VipMember {
	private int memberNo;
	private String memberNickname;
	private String membershipGrade;
	private int membershipLevel;
	private int percent;
	private String membershipRecodeLast;
}
