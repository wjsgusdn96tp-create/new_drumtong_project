package kr.co.iei.etc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/error")
public class ErrorController {
	@RequestMapping(value="/notFound")
	public String notFound() {
		return "error/notFound";
	}
	@RequestMapping(value="/serverError")
	public String serverError() {
		return "error/serverError";
	}
}
