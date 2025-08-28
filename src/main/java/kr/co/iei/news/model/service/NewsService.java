package kr.co.iei.news.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.news.model.dao.NewsDao;
import kr.co.iei.news.model.vo.Discount;
import kr.co.iei.news.model.vo.News;
import kr.co.iei.news.model.vo.Notice;

@Service
public class NewsService {
	
	@Autowired
	private NewsDao newsDao;

	public HashMap<String, Object> selectAllNotice(int noticeReqPage) {
		int numPerPage=20;
		int start = (noticeReqPage-1)*numPerPage+1; //한 페이지에 시작 글
		int end = start+numPerPage-1; // 한 페이지의 끝이 되는 글
		HashMap<String, Object> pageNum = new HashMap<String, Object>();
		pageNum.put("start",start);
		pageNum.put("end", end);
		List<Notice> notice = newsDao.selectAllNotice(pageNum);
		
		
		int totalListCount = newsDao.countAllNotice(); // 전체 글 수 
		int totalPage = (int)Math.ceil(totalListCount/(double)numPerPage); // 전체 페이지 개수 구하기
		int pageNaviSize = 5;
		int startPageNum = (int)Math.ceil(noticeReqPage/(double)pageNaviSize); // navi 첫번째 칸에 들어가 수 계산
		
		/*
		 <ul>
		 	<li><a><span>'<'</span></a></li>
		 	<li><a> startPageNum </a> </li>
		 	<li><a> startPageNum+1 </a> </li>
		 	<li><a> startPageNum+2 </a> </li>
		 	<li><a> startPageNum+3 </a> </li>
		 	<li><a> startPageNum+4 </a> </li>
		 	<li><a><span>'>'</span></a></li>
		 </ul>
		
		 */
		String pageNavi = "<ul>";
			
		
		if(startPageNum != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/news/list?noticeReqPage="+(startPageNum-1)+"'>";
			pageNavi += "<span class='material-icons'> chevron_left </span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		for(int i=0; i<pageNaviSize; i++) {
			pageNavi += "<li>";
			if(startPageNum == noticeReqPage) {
				pageNavi += "<a href='/news/list?noticeReqPage="+startPageNum+"'>";
			} else {
				pageNavi += "<a href='/news/list?noticeReqPage="+startPageNum+"'>";				
			}
			pageNavi += startPageNum;
			pageNavi += "</a>";
			pageNavi += "</li>";
			
			startPageNum++;
			if(startPageNum > totalPage) {
				break;
			}
		}
		// 다음버튼
		if(startPageNum <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a href='/news/list?noticeReqPage="+startPageNum+"'>";
			pageNavi += "<span class='material-icons'> chevron_right </span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		pageNavi += "</ul>"; 
		
		pageNavi += "";
		
		HashMap<String, Object> noticeAll = new HashMap<String, Object>(); // list와 navi 묶어서 보내기
		noticeAll.put("notice", notice);
		noticeAll.put("pageNavi", pageNavi);
		return noticeAll;
	}

	public Notice selectOneNotice(int noticeNo) {
		Notice notice = newsDao.selectOneNotice(noticeNo);
		return notice;
	}

	public int selectTotalNewsCount() {
		int totalCount = newsDao.selectTotalNewsCount();
		return totalCount;
	}
	
	@Transactional
	public int insertNews(News news) {
		int result = newsDao.intsertNews(news);
		return result;
	}
	
	@Transactional
	public int insertDiscount(Discount discount) {
		int result = newsDao.insertDiscount(discount);
		return result;
	}

}
