package kr.co.iei.news.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.news.model.vo.Discount;
import kr.co.iei.news.model.vo.News;
import kr.co.iei.news.model.vo.Notice;

@Mapper
public interface NewsDao {

	List<Notice> selectAllNotice(HashMap<String, Object> pageNum);

	int countAllNotice();

	Notice selectOneNotice(int noticeNo);

	int selectTotalNewsCount();

	int intsertNews(News news);

	int insertDiscount(Discount discount);

}
