package kr.co.iei.member.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value = "member")
public class Member {
	private int memberNo;
	private String memberEmail;
	private String memberNicname;
	private String memberPw;
	private String memberGrade;
}
