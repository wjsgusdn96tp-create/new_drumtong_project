package kr.co.iei.news.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.news.model.vo.Notice;

@Mapper
public interface NewsDao {

	List<Notice> selectAllNotice(HashMap<String, Object> pageNum);

	int countAllNotice();

}
