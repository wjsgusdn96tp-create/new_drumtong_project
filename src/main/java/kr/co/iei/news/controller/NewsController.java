package kr.co.iei.news.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import kr.co.iei.customer.controller.CustomerController;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.news.model.service.NewsService;
import kr.co.iei.news.model.vo.Discount;
import kr.co.iei.news.model.vo.News;
import kr.co.iei.news.model.vo.Notice;
import kr.co.iei.news.model.vo.Poster;
import kr.co.iei.util.FileUtil;


@Controller
@RequestMapping("news")
public class NewsController {

    private final CustomerController customerController;


	
	@Autowired
	private NewsService newsService;
	
	@Value(value="${file.root}")
	private String root;
	
	@Autowired
	private FileUtil fileUtil;

    NewsController(CustomerController customerController) {
        this.customerController = customerController;
    }
	
	@GetMapping(value="/list")
	public String newsList(int noticeReqPage, String tab, Model model) {
		
		// notice 정보
		HashMap<String, Object> noticeAll = newsService.selectAllNotice(noticeReqPage);
		model.addAttribute("notice", noticeAll.get("notice"));
		model.addAttribute("pageNavi", noticeAll.get("pageNavi"));
		int totalCount = newsService.selectTotalNewsCount(tab);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("tab", tab);
		
		//배너 정보
		Poster banner = newsService.selectNewsPoster();
		model.addAttribute("banner", banner);
		
		List<News> newsAllList = newsService.selectAllNewsBanner();
		model.addAttribute("newsAllList", newsAllList);
		
		News newsModal = newsService.selectOneNewsModal();
		model.addAttribute("newsModal", newsModal);
		model.addAttribute("modal", 1);
		return "news/list";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm(Model model) {
		List product = newsService.selectAllProduct();
		model.addAttribute("product", product);
		return "news/writeFrm";
	}
	@PostMapping(value="write") //뉴스 등록하기 누르면 호출
	public String writeNews(News news, String productNoStr, String discountType, String discountPrice, MultipartFile newsImageFile, @SessionAttribute(required = false) Member member) {
		int memberNo = member == null ? 0 : member.getMemberNo();
		
		//productNo list 확인
		String[] productList;
		if(productNoStr != null) {	
			productList = productNoStr.split(",");
		} else {
			productList = null;
		}
		
		//file upload
		String savepath = root+"/news/";
		String filepath = fileUtil.upload(savepath, newsImageFile);
		news.setImage(filepath);
		news.setMemberNo(memberNo);
		
		System.out.println(news);
		System.out.println(productList);
		System.out.println(discountType);
		System.out.println(discountPrice);
		int result = newsService.insertNews(news, productList, discountType, discountPrice);		
		
		return "redirect:/news/list?noticeReqPage=1&tab=all&modal=1";
	}
	
	
	@GetMapping(value="updateNews")
	public String updateNews(int newsNo, Model model) {
		
		News news = newsService.selectOneNews(newsNo);
		model.addAttribute("news", news);
		
		List product = newsService.selectAllProduct();
		model.addAttribute("product", product);
		
		List discount = newsService.selectAllDiscount(newsNo);
		System.out.println(discount);
		if(!discount.isEmpty()) {
			Discount d = (Discount)discount.get(0);
			if(d.getDiscountPercent()== 0) {
				d.setDiscountType("Price");
				model.addAttribute("discountPrice",d.getDiscountPrice());	
				
			} else {
				d.setDiscountType("Percent");
				model.addAttribute("discountPrice",d.getDiscountPercent());
			}
		} else {
			model.addAttribute("discountPrice",0);
			model.addAttribute("discountType",0);
		}
		
		model.addAttribute("discount", discount);
		return "news/updateNews";
	}
	
	@PostMapping(value="updatewrite")
	public String updateWrite(News news, String productNoStr, String discountType, String discountPrice,@SessionAttribute(required = false) Member member) {
		int memberNo = member == null ? 0 : member.getMemberNo();
		news.setMemberNo(memberNo);
		
		String[] productList;
		if(productNoStr != null) {	
			productList = productNoStr.split(",");
		} else {
			productList = null;
		}
		
		int result = newsService.updateNews(news, productList, discountType, discountPrice);		
		
		return "redirect:/news/view?newsNo="+news.getNewsNo();
	}
	
	@GetMapping(value="/noticeWriteFrm")
	public String noticeWriteFrm() {
		return "news/noticeWriteFrm";
	}
	
	@GetMapping(value="/noticeWrite")
	public String noticeWrite(Notice notice, @SessionAttribute(required = false) Member member) {
		int memberNo = member == null ? 0 : member.getMemberNo();
		if(memberNo != 0 )  {
			notice.setMemberNo(memberNo);
			int result = newsService.insertNotice(notice);			
			
		}
		
		return "redirect:/news/list?noticeReqPage=1&tab=all&modal=1";
	}
	
	@GetMapping(value="/noticeView")
	public String noticeView(int noticeNo, Model model) {
		Notice notice = newsService.selectOneNotice(noticeNo);
		model.addAttribute("notice", notice);
		return "news/noticeView";
	}

	@ResponseBody
	@GetMapping(value="/more")
	public List more(int start, int amount, String tab, @SessionAttribute(required = false) Member member) {
		int memberNo = member == null ? 0 : member.getMemberNo();
		List newsList = newsService.selectAllNews(start, amount, memberNo, tab);
		
		return newsList;
	}
	
	@ResponseBody
	@PostMapping(value="/likepush")
	public int likepush(News news, @SessionAttribute Member member) {
		int memberNo = member == null ? 0 : member.getMemberNo();
		int likeCount = newsService.likepush(news, memberNo);

		return likeCount;
	}
	
	
	@GetMapping(value="/view")
	public String newsView(int newsNo, Model model) {
		News news = newsService.selectOneNews(newsNo);
		model.addAttribute("news", news);
		return "news/view";
	}
	
	
	@GetMapping(value="deleteNews")
	public String deleteNews(int newsNo) {
		Poster posterMain = newsService.selectMainPoster();
		Poster posterNews = newsService.selectNewsPoster();
		if(newsNo !=posterMain.getNewsNo() && newsNo != posterNews.getNewsNo()) {
			int result = newsService.deleteNews(newsNo);
		} 
		
		return "redirect:/news/list?noticeReqPage=1&tab=all&modal=1";
	}
	
	@GetMapping(value="bannerWrite")
	public String bannerWrite( Model model) {
		List newsList = newsService.selectAllNewsBanner();
		model.addAttribute("newsList", newsList);
		
		return "news/bannerWrite";
	}
	
	@PostMapping(value="banner")
	public String banner(Poster poster, MultipartFile posterImageFile) {
		String savepath = root+"/banner/";
		String filepath = fileUtil.upload(savepath, posterImageFile);
		poster.setImage(filepath);
		int result = newsService.insertPoster(poster);
		
		return "redirect:/";
	}
	
	@GetMapping(value="bannerSelect")
	public String bannerSelect(Model model) {
		List newsList = newsService.selectAllNewsBanner();
		model.addAttribute("newsList", newsList);
		
		List posterList = newsService.selectAllPoster();
		model.addAttribute("posterList",posterList);
		
		Poster imageMain = newsService.selectMainPoster();
		model.addAttribute("imageMain", imageMain);
		
		Poster imageNews = newsService.selectNewsPoster();
		model.addAttribute("imageNews", imageNews);
		return "news/bannerSelect";
	}
	
	@GetMapping(value="modalChange")
	public void modalChange(int newsNo) {
		int result = newsService.changeModal(newsNo);		
	}
	
	@PostMapping(value="bannerChange")
	public String bannerChange(int posterNo, int posterNoNews) {
		int result = newsService.changeBannerMain(posterNo);
		result += newsService.changeBannerNews(posterNoNews);
		return "redirect:/";
	}
	
	@GetMapping(value="bannerDelete")
	public String bannerDelete(Model model) {
		List posterList = newsService.selectAllPoster();
		model.addAttribute("posterList",posterList);
		
		return "news/bannerDelete";
	}
	@GetMapping(value="deleteBannerComplete")
	public String deleteBannerComplete(String posterNo) {
		
		
		String[] posterNoList;
		if(posterNo != null) {	
			posterNoList = posterNo.split(",");
		} else {
			posterNoList = null;
		}
		int result = newsService.deleteBanner(posterNoList);		
		return  "redirect:/news/list?noticeReqPage=1&tab=all&modal=1";
	}
	@ResponseBody
	@GetMapping(value="noticeDelete")
	public void  noticeDelete(int noticeNo) {
		int result = newsService.deleteNotice(noticeNo);
	}
	
	@GetMapping(value="noticeUpdateFrm")
	public String noticeUpdateFrm(int noticeNo, Model model) {
		Notice notice = newsService.selectOneNotice(noticeNo);
		model.addAttribute("notice", notice);
		
		return "news/noticeUpdateFrm";
	}
	
	@GetMapping(value="noticeUpdate")
	public String noticeupdate(Notice notice, @SessionAttribute(required = false) Member member) {
		if(member != null) {
			notice.setMemberNo(member.getMemberNo());
			int result = newsService.updateNotice(notice);
		}
		return  "redirect:/news/list?noticeReqPage=1&tab=all&modal=1";
	}
}

