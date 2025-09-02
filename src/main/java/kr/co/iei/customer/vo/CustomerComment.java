package kr.co.iei.customer.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="customerComment")
public class CustomerComment {
	
	private int commentNo;	//댓글번호	
	private int customerNo;	//글번호	/ customer_service_tbl(customer_no) 레퍼런스
	private int customerServiceRef;	//게시글참조 / customer_service_tbl 레퍼런스
	private int customerCommentRef; //댓글참조 / customer_comment 레퍼런스
	private String commentContent; //댓글내용
	private String commentDate; // 댓글날짜
	private int customerWriterNo; // 회원번호 / member_tbl(member_no) 레퍼런스
	
}
