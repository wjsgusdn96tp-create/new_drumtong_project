package kr.co.iei.news.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import kr.co.iei.customer.controller.CustomerController;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.news.model.service.NewsService;
import kr.co.iei.news.model.vo.Discount;
import kr.co.iei.news.model.vo.News;
import kr.co.iei.news.model.vo.Notice;
import kr.co.iei.product.vo.Product;
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
		
		return "news/list";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		return "news/writeFrm";
	}
	@PostMapping(value="write") //뉴스 등록하기 누르면 호출
	public String writeNews(News news, MultipartFile newsImageFile, @SessionAttribute(required = false) Member member) {
		int memberNo = member == null ? 0 : member.getMemberNo();
		String savepath = root+"/news/";
		String filepath = fileUtil.upload(savepath, newsImageFile);
		news.setImage(filepath);
		news.setMemberNo(memberNo);
		
		int result = newsService.insertNews(news);
			
		return "redirect:/news/list?noticeReqPage=1&tab=all";
	}
	
	@GetMapping(value="/noticeWriteFrm")
	public String noticeWriteFrm() {
		return "news/noticeWriteFrm";
	}
	
	@GetMapping(value="/noticeWrite")
	public String noticeWrite(Notice notice, @SessionAttribute(required = false) Member member) {
		int memberNo = member == null ? 0 : member.getMemberNo();
		notice.setMemberNo(memberNo);
		int result = newsService.insertNotice(notice);
		return "redirect:/news/list?noticeReqPage=1";
	}
	
	@GetMapping(value="/noticeView")
	public String noticeView(int noticeNo, Model model) {
		Notice notice = newsService.selectOneNotice(noticeNo);
		model.addAttribute("notice", notice);
		return "news/noticeView";
	}
	
	@GetMapping(value="/discountWriteFrm")
	public String discountWriteFrm(String title, String newsNo, Model model) {
		model.addAttribute("title", title);
		model.addAttribute("newsNo", newsNo);
		List product = newsService.selectAllProduct();
		model.addAttribute("product", product);
		return "news/discountWriteFrm";
	}
	
	@GetMapping(value="/discountWrite")
	public String discountWrite(News news, String discountSelect, String discountPrice, String productNo, @SessionAttribute(required = false) Member member) {
		String[] list = productNo.split(",");
		int result = newsService.insertDiscount(news, discountSelect, discountPrice, list);
		return "redirect:/news/list?noticeReqPage=1&tab=all";
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
	
}


