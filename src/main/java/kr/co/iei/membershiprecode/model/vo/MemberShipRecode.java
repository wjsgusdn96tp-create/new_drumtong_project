package kr.co.iei.membershiprecode.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value = "membershiprecode")
public class MemberShipRecode {
	private int membershiprecodeNo;
	private String membershiprecodeStart;
	private String membershiprecodeLast;
	private String membershiprecodeNickname;
	private int membershiprecodeMemberNo;
}
