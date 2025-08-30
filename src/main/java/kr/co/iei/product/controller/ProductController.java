package kr.co.iei.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.product.service.ProductService;
import kr.co.iei.product.vo.Product;
import kr.co.iei.product.vo.productListDate;
import kr.co.iei.util.FileUtil;

@Controller
@RequestMapping(value="/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Value(value="${file.root}")
	private String root;
	
	@Autowired
	private FileUtil fileUtil;
	
	@GetMapping(value="/productList")
	private String productList(int reqPage, String shopName, Model model) {
		productListDate pld = productService.productListDate(reqPage);
		model.addAttribute("list", pld.getList());
		model.addAttribute("pageNavi",pld.getPageNavi());
		model.addAttribute("shopName",shopName);
		return "product/productList";
	}
	
	

	@GetMapping(value="/productInsertFrm")
	public String ProductInsertFrm() {
		return "product/productInsertFrm";
	}
	@GetMapping(value = "/productInsertDrink")
	public String productInsertDrink(Model model, String drink) {
		model.addAttribute("productTitle",drink);
		return "product/productInsertDrink";
	}
	@GetMapping(value = "/productInsertDessert")
	public String productInsertDessert(Model model, String dessert) {
		model.addAttribute("productTitle",dessert);
		return "product/productInsertDessert";
	}
	@GetMapping(value = "/productInsertGoods")
	public String productInsertGoods(Model model, String goods) {
		model.addAttribute("productTitle",goods);
		return "product/productInsertGoods";
	}
	
	@PostMapping(value="/productInsertDrink")
	/*대표상품인지 아닌지도 값 받아와야함*/
	public String productInsertDrink(Product p, MultipartFile imageFile, String productBestTbl,Model model){
		/*저장 경로 root에 서자명 컴퓨터에는 productinsert 폴더 만들었는데 다른분들 안 만들어도 상관없음??*/
		String savepath = root+"/productInsert/";
		System.out.println(imageFile);
		String filepath = fileUtil.upload(savepath,imageFile);
		p.setProductImg(filepath);
		
		if(p.getProductTitleDetail().equals("iceCoffe") || 
			p.getProductTitleDetail().equals("iceTea") || 
			p.getProductTitleDetail().equals("iceOtherDrink")) {
			p.setProductTilteTemp("ice");
		}else if(p.getProductTitleDetail().equals("hotCoffe") || 
				p.getProductTitleDetail().equals("hotTea")) {
			p.setProductTilteTemp("hot");
		}
		
		int result = productService.productInsertDrink(p, productBestTbl);
		
		if(result == 0) {
			model.addAttribute("title", "상품 등록 실패!!");
			model.addAttribute("text", "상품 등록 실패 되었습니다.");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/product/productInsertFrm" ); //reqPage 반드시 줘야 함. 
			return "common/msg";
		}else if(result == 10) {
			model.addAttribute("title", "대표 상품 등록 실패(일반 상품으로 등록)!!!");
			model.addAttribute("text", "대표 상품으로 등록 원할 시 [1.상품 수정 2.삭제 후 재등록] 진행 해주세요.");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/product/productList" ); //reqPage 반드시 줘야 함. 
			return "common/msg";
		}else if(result == 1) {
			model.addAttribute("title", "상품 등록 성공!!");
			model.addAttribute("text", "상품 등록 성공 했습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/product/productList" ); //reqPage 반드시 줘야 함. 
			return "common/msg";
		}
	
		return "product/productList";
	}
	

	
}
