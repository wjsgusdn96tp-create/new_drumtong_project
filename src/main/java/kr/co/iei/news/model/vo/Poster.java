package kr.co.iei.news.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value="poster")
public class Poster {
	private int posterNo;
	private String mainYn;
	private String newsYn;
	private int newsNo;
	private String image;
	
}
