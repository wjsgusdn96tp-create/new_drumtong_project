package kr.co.iei.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.product.service.ProductService;

@Controller
@RequestMapping(value="/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping(value="/productList")
	public String ProductList() {
		return "/product/productList";
	}
	@GetMapping(value="/productInsertFrm")
	public String ProductInsertFrm() {
		return "/product/productInsertFrm";
	}
}
