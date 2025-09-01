package kr.co.iei.customer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.customer.service.CustomerService;
import kr.co.iei.customer.vo.Customer;
import kr.co.iei.customer.vo.CustomerComment;
import kr.co.iei.customer.vo.CustomerListData;
import kr.co.iei.customer.vo.CustomerServiceFile;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.util.FileUtil;

@Controller
@RequestMapping(value="/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtil fileUtil;
	
	@GetMapping(value="/list")
	public String customerList(int reqPage, String sort, Model model, @SessionAttribute(required = false) Member member) {
		CustomerListData cld = customerService.selectCustomerList(reqPage, sort, member, null);
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/list";
	}
	
	@GetMapping(value="/goodjob")
	public String goodjobList(int reqPage, String sort, String category, Model model, @SessionAttribute(required = false) Member member) {
		CustomerListData cld = customerService.selectCustomerList(reqPage, sort, member, "칭찬");
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/listGj";
	}
	
	@GetMapping(value="/complain")
	public String complainList(int reqPage, String sort, Model model, @SessionAttribute(required = false) Member member) {
		CustomerListData cld = customerService.selectCustomerList(reqPage, sort, member, "불만");
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/listComplain";
	}
	
	@GetMapping(value="/opinion")
	public String opinionList(int reqPage, String sort, Model model, @SessionAttribute(required = false) Member member) {
		CustomerListData cld = customerService.selectCustomerList(reqPage, sort, member, "의견");
		model.addAttribute("list", cld.getList());
		model.addAttribute("navi", cld.getCustomerNavi());
		model.addAttribute("sort", sort);
		return "customer/listOpinion";

	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm(Customer customer, Model model) {
		model.addAttribute("c", customer);
		
		return "customer/writeFrm";
	}
	
	@PostMapping(value="/write")
	public String write(Customer customer, MultipartFile[] upFile, Model model) {
		
		List<CustomerServiceFile> fileList = new ArrayList<CustomerServiceFile>();
		
		if(!upFile[0].isEmpty()) {
			
			String savepath = root + "/customer/";
			
			for(MultipartFile file : upFile) {
				
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
				System.out.println("filename : "+filename);
				System.out.println("filepath : "+filepath);
				
				CustomerServiceFile customerServiceFile = new CustomerServiceFile();
				customerServiceFile.setFileName(filename);
				customerServiceFile.setFilePath(filepath);
				fileList.add(customerServiceFile);
			}
		}
		
		int result = customerService.insertCustomer(customer,fileList);
		model.addAttribute("title","작성 완료!");
		model.addAttribute("text","게시글이 등록되었습니다.");
		model.addAttribute("icon","success");
		model.addAttribute("loc", "/customer/list?reqPage=1");
		return "common/msg";
	}
	
	@GetMapping(value="/view")
	public String CustomerView(int customerNo, @SessionAttribute(required = false) Member member, Model model) {
		Customer c = customerService.selectOneCustomer(customerNo);
		
		model.addAttribute("c", c);
		
		return "customer/view";
	}
	
	@PostMapping(value="/delete")
	@ResponseBody
	public String deleteCustomer(int customerNo, @SessionAttribute(required = false) Member member, Model model) {
		Customer c = customerService.selectOneCustomer(customerNo);
		CustomerListData cld = customerService.deleteCustomer(customerNo);
		int delResult = cld.getDelResult();
//		System.out.println(delResult);
		
		if (c != null && c.getCustomerNickname().equals(member.getMemberNickname())) {
			String savepath = root+"/customer";
			if (delResult > 0) {
				return "success"; 
			}
		}
		return "fail";
	}
	
	@PostMapping(value="/insertComment")
	public String insertComment(CustomerComment cc) {
		
		int result = customerService.insertCustomerComment(cc);
		return "redirect:/customer/view?customerNo="+cc.getCustomerServiceRef();
	}
	
	@PostMapping(value="customer/deleteComment")
	public String deleteComment(int commentNo) {
		int result = customerService.deleteComment(commentNo);
		System.out.println(result);
		if(result > 0) {
			return "success";
		}
		return "fail";
	}
}















