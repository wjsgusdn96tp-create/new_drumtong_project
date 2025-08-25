package kr.co.iei.promotion.model.news.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value="news")
public class News {
	private int newsNo;
	private int memberNo;
	private String type;
	private String startDate;
	private String endDate;
	private String title;
	private String content;
	private String writeDate;
	private String image;
}
