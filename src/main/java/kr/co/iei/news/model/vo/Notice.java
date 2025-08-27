package kr.co.iei.news.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value="notice")
public class Notice {
	private int noticeNo;
	private int memberNo;
	private String title;
	private String content;
	private String writeDate;
	private int viewCount;
}
