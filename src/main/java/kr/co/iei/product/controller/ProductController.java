package kr.co.iei.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	@GetMapping(value = "/productInsertDrink")
	public String productInsertDrink(Model model, String drink) {
		model.addAttribute("productTitle",drink);
		return "/product/productInsertDrink";
	}
	@GetMapping(value = "/productInsertDessert")
	public String productInsertDessert(Model model, String dessert) {
		model.addAttribute("productTitle",dessert);
		return "/product/productInsertDessert";
	}
	@GetMapping(value = "/productInsertGoods")
	public String productInsertGoods(Model model, String goods) {
		model.addAttribute("productTitle",goods);
		return "/product/productInsertGoods";
	}
}
