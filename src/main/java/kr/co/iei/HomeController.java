package kr.co.iei;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.iei.news.model.service.NewsService;

@Controller
public class HomeController {
	@Autowired
	NewsService newsService;
	
	@GetMapping(value="/")
	public String main(Model model) {
		List product = newsService.selectBestProduct(); 
		model.addAttribute("product", product);
		return "index";
	}
	
}
