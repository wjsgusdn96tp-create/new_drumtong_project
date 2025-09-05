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
import kr.co.iei.news.model.vo.Poster;

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

	public int selectTotalNewsCount(String tab) {
		int totalCount = newsDao.selectTotalNewsCount(tab);
		return totalCount;
	}
	
	@Transactional
	public int insertNews(News news, String[] productList, String discountType, String discountPrice) {
		int newsNo = newsDao.getNewsNo();
		news.setNewsNo(newsNo);
		
		int result = newsDao.insertNews(news);
		if(productList != null) {
			for(int i=0 ;i<productList.length;i++) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("newsNo", newsNo);
				param.put("productNo", productList[i]);
				param.put("discountType", discountType);
				param.put("discountPrice", discountPrice);
				result += newsDao.insertDiscount(param);			
			}
			
		}
		
		return result;
	}

	public List selectAllNews(int start, int amount, int memberNo, String tab) {

		int end = start+amount -1;
		HashMap<String, Object> newsListNum = new HashMap<String, Object>();
		newsListNum.put("start", start);
		newsListNum.put("end", end);
		newsListNum.put("tab", tab);
		List news = newsDao.selectAllNews(newsListNum);
		for(int i=0 ;i<news.size();i++) {
			News n = (News)news.get(i);
			n.setMemberNo(memberNo);
			News likeCountNews = newsDao.selectNewsLikeCount(n.getNewsNo());
			if(likeCountNews != null) {
				n.setLikeCount(likeCountNews.getLikeCount());
			}
			
			int isLike = newsDao.selectNewsisLike(n);
			n.setIsLike(isLike);
			news.set(i, n);
		}
		return news;
	}
	
	@Transactional
	public int insertNotice(Notice notice) {
		int result = newsDao.insertNotice(notice);
		return result;
	}
	
	
	@Transactional
	public int likepush(News news, int memberNo) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("newsNo", news.getNewsNo());
		param.put("memberNo", memberNo);
		
		news.setMemberNo(memberNo);
		int isLike = newsDao.selectNewsisLike(news);
		
		int result = 0;
		if(isLike ==0) {
			result = newsDao.insertNewsLike(param);
		} else {
			result = newsDao.deleteNewsLike(param);
 		}
		int likeCount = 0;
		News likeCountNews = newsDao.selectNewsLikeCount(news.getNewsNo());
		if(likeCountNews != null) {
			likeCount = likeCountNews.getLikeCount();
		}
		
		return likeCount;
	}

	public List selectAllProduct() {
		List product = newsDao.selectAllProduct();
		return product;
	}

	public List selectAllDiscount(int newsNo) {
		List discount = newsDao.selectAllDiscount(newsNo);
		return discount;
	}

	public News selectOneNews(int newsNo) {
		News news = newsDao.selectOneNews(newsNo);
		return news;
	}

	public List selectBestProduct() {
		List product = newsDao.selectBestProduct();
		
		return product;
	}

	@Transactional
	public int updateNews(News news, String[] productList, String discountType, String discountPrice) {
		
		int result = newsDao.updateNews(news);
		if(productList != null) {
			for(int i=0; i<productList.length;i++) {
				Discount discount = new Discount();
				discount.setNewsNo(news.getNewsNo());
				discount.setProductNo(Integer.parseInt(productList[i]));
				if(discountType.equals("Percent")) {
					discount.setDiscountPercent(Integer.parseInt(discountPrice));
				} else if(discountType.equals("Price")) {
					discount.setDiscountPrice(Integer.parseInt(discountPrice));
				}
				
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("newsNo", news.getNewsNo());
				param.put("productNo", productList[i]);
				param.put("discountType", discountType);
				param.put("discountPrice", discountPrice);
				
				result = newsDao.deleteDiscount(param);
				result = newsDao.insertDiscount(param);
			}		
			
		}
		
		return result;
	}
	@Transactional
	public int deleteNews(int newsNo) {
		int result = newsDao.deleteNews(newsNo);
		return 0;
	}

	public List selectAllNewsBanner() {
		List newsList = newsDao.selectAllNewsBanner();
		
		return newsList;
	}

	@Transactional
	public int insertPoster(Poster poster) {
		int result = newsDao.insertPoster(poster);
		return result;
	}

	public List selectAllPoster() {
		List posterList = newsDao.selectAllPoster();
		return posterList;
	}

	public Poster selectMainPoster() {
		Poster imageMain = newsDao.selectMainPoster();
		return imageMain;
	}

	public Poster selectNewsPoster() {
		Poster imageNews = newsDao.selectNewsPoster();
		return imageNews;
	}

	public News selectOneNewsModal() {
		News newsModal = newsDao.selectOneNewsModal();
		return newsModal;
	}
	
	@Transactional
	public int changeModal(int newsNo) {
		int result = newsDao.deleteModal();
		result += newsDao.insertModal(newsNo);
		return result;
	}
	
	@Transactional
	public int changeBannerMain(int posterNo) {
		int result = newsDao.insertBannerMain(posterNo);
		result += newsDao.deleteBannerMain(posterNo);
		return 0;
	}
	
	@Transactional
	public int changeBannerNews(int posterNo) {
		int result = newsDao.insertBannerNews(posterNo);
		result += newsDao.deleteBannerNews(posterNo);
		return 0;
	}
	
	@Transactional
	public int deleteBanner(String[] posterNoList) {
		int result = 0;
		for(int i=0;i<posterNoList.length ;i++ ) {
			int posterNo = Integer.parseInt(posterNoList[i]);
			
			result += newsDao.deleteBanner(posterNo);
		}
		return result;
	}
	@Transactional
	public int deleteNotice(int noticeNo) {
		int result = newsDao.deleteNotice(noticeNo);
		return result;
	}
	@Transactional
	public int updateNotice(Notice notice) {
		int result= newsDao.updateNotice(notice);
		return result;
	}
	
	@Transactional
	public int viewUpdate(int viewCount, int noticeNo) {
		
		HashMap<String , Object> param = new HashMap<String , Object>();
		param.put("viewCount", viewCount);
		param.put("noticeNo", noticeNo);
		int result = newsDao.viewUpdate(param);
		
		return result;
	}

	
}
