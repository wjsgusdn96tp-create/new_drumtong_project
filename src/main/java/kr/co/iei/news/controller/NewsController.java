package kr.co.iei.news.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import kr.co.iei.customer.controller.CustomerController;
import kr.co.iei.customer.service.CustomerService;
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

    private final CustomerService customerService;

    private final CustomerController customerController;
	
	@Autowired
	private NewsService newsService;
	
	@Value(value="${file.root}")
	private String root;
	
	@Autowired
	private FileUtil fileUtil;

    NewsController(CustomerController customerController, CustomerService customerService) {
        this.customerController = customerController;
        this.customerService = customerService;
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
		String[] productList = productNoStr.split(",");
		
		String savepath = root+"/news/";
		String filepath = fileUtil.upload(savepath, newsImageFile);
		news.setImage(filepath);
		news.setMemberNo(memberNo);
		
		int result = newsService.insertNews(news, productList, discountType, discountPrice);		
		
		return "redirect:/news/list?noticeReqPage=1&tab=all";
	}
	
	@GetMapping(value="/noticeWriteFrm")
	public String noticeWriteFrm() {
		return "news/noticeWriteFrm";
	}
	
	@GetMapping(value="/noticeWrite")
	public String noticeWrite(Notice notice, @SessionAttribute(required = false) Member member) {
		int memberNo = member == null ? 0 : member.getMemberNo();
		
		int result = newsService.insertNotice(notice);
		return "redirect:/news/list?noticeReqPage=1&tab=all";
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
	public String newsView(String newsNo, Model model) {
		News news = newsService.selectOneNews(newsNo);
		model.addAttribute("news", news);
		return "news/view";
	}
	
	@GetMapping(value="updateNews")
	public String updateNews(String newsNo, Model model) {
		News news = newsService.selectOneNews(newsNo);
		model.addAttribute("news", news);
		
		List product = newsService.selectAllProduct();
		model.addAttribute("product", product);
		
		List discount = newsService.selectAllDiscount(newsNo);
		Discount d = (Discount)discount.get(0);
		
		if(d.getDiscountPercent()== 0) {
			model.addAttribute("discountPrice",d.getDiscountPrice());
		} else {
			model.addAttribute("discountPrice",d.getDiscountPercent());
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
	
	@GetMapping(value="deleteNews")
	public String deleteNews(int newsNo) {
		int result = newsService.deleteNews(newsNo);
		
		return "redirect:/news/list?noticeReqPage=1&tab=all";
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
}


