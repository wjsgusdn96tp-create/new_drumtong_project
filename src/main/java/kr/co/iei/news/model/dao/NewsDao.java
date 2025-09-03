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

	int selectTotalNewsCount(String tab);

	int insertNews(News news);

	int insertDiscount(HashMap<String, Object> param);

	List<News> selectAllNews(HashMap<String, Object> newsListNum);

	int insertNotice(Notice notice);

	int insertNewsLike(HashMap<String, Object> param);

	int deleteNewsLike(HashMap<String, Object> param);

	News selectNewsLikeCount(int newsNo);

	int selectNewsisLike(News n);

	List selectAllProduct();

	List selectAllDiscount(String newsNo);

	News selectOneNews(String newsNo);

	List selectBestProduct();

}
