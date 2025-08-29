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

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.news.model.service.NewsService;
import kr.co.iei.news.model.vo.Discount;
import kr.co.iei.news.model.vo.News;
import kr.co.iei.news.model.vo.Notice;
import kr.co.iei.util.FileUtil;


@Controller
@RequestMapping("news")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	@Value(value="${file.root}")
	private String root;
	
	@Autowired
	private FileUtil fileUtil;
	
	@GetMapping(value="/list")
	public String newsList(int noticeReqPage, Model model) {
		HashMap<String, Object> noticeAll = newsService.selectAllNotice(noticeReqPage);
		model.addAttribute("notice", noticeAll.get("notice"));
		model.addAttribute("pageNavi", noticeAll.get("pageNavi"));
		int totalCount = newsService.selectTotalNewsCount();
		model.addAttribute("totalCount", totalCount);
		
		return "news/list";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		return "news/writeFrm";
	}
	@PostMapping(value="write") //뉴스 등록하기 누르면 호출
	public String writeNews(News news, MultipartFile newsImageFile, @SessionAttribute(required = false) Member member) {
		String savepath = root+"/news/";
		String filepath = fileUtil.upload(savepath, newsImageFile);
		news.setImage(filepath);
		news.setMemberNo(member.getMemberNo());
		
		int result = newsService.insertNews(news);
			
		return "redirect:/news/list?noticeReqPage=1";
	}
	
	@GetMapping(value="/noticeWriteFrm")
	public String noticeWriteFrm() {
		return "news/noticeWriteFrm";
	}
	
	@GetMapping(value="/noticeWrite")
	public String noticeWrite(Notice notice, @SessionAttribute(required = false) Member member) {
		notice.setMemberNo(member.getMemberNo());
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
		return "news/discountWriteFrm";
	}
	
	@GetMapping(value="/discountWrite")
	public String discountWrite(Discount discount,List productNoList, @SessionAttribute(required = false) Member member) {
		System.out.println(discount);
		int result = newsService.insertDiscount(discount, productNoList);
		return "redirect:/news/list?noticeReqPage=1";
	}
	
	@ResponseBody
	@GetMapping(value="/more")
	public List more(int start, int amount) {
		List newsList = newsService.selectAllNews(start, amount);

		return newsList;
	}
	
	
}


