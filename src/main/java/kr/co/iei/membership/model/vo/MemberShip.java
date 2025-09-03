package kr.co.iei.membership.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value = "membership")
public class MemberShip {
	private int membershipLevel;
	private String membershipGrade;
	private int membershipPrice;
	private int percent;
}
