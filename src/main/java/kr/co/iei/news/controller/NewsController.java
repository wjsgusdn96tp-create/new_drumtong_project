package kr.co.iei.news.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.news.model.service.NewsService;
import kr.co.iei.news.model.vo.Notice;

@Controller
@RequestMapping("news")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	@GetMapping(value="/list")
	public String newsList(int noticeReqPage, Model model) {
		HashMap<String, Object> noticeAll = newsService.selectAllNotice(noticeReqPage);
		model.addAttribute("notice", noticeAll.get("notice"));
		model.addAttribute("pageNavi", noticeAll.get("pageNavi"));
		return "news/list";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		return "news/writeFrm";
	}
	
	@GetMapping(value="/noticeWriteFrm")
	public String noticeWriteFrm() {
		return "news/noticeWriteFrm";
	}
}
