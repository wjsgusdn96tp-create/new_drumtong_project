package kr.co.iei.customer.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.iei.customer.service.CustomerService;
import kr.co.iei.customer.vo.Customer;
import kr.co.iei.customer.vo.CustomerComment;
import kr.co.iei.customer.vo.CustomerListData;
import kr.co.iei.customer.vo.CustomerServiceFile;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.util.FileUtil;

@Controller
@RequestMapping(value="/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	@Autowired
	MemberService memberService;
	
	@Value("${file.root}") // application.properties의 file.root 값을 가져옴
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
		List<Member> allMemberList = customerService.selectAllMember();
		
		
		
//		String memberEmail = member.getMemberEmail();
//		Member allMemberList = memberService.selectOneMember(memberEmail);
		
		model.addAttribute("c", c);
		model.addAttribute("allMembers", allMemberList);
		return "customer/view";
	}
	
	@PostMapping(value="/delete")
	@ResponseBody
	public String deleteCustomer(int customerNo, @SessionAttribute(required = false) Member member) {
		Customer c = customerService.selectOneCustomer(customerNo);
		
		if (c != null && c.getCustomerNickname().equals(member.getMemberNickname())) {
			int result = customerService.deleteCustomer(customerNo);
			if (result > 0) {
				return "success"; 
			}
		}
		return "fail";
	}
	
	@PostMapping(value="/insertComment")
	public String insertComment(CustomerComment cc) {
		
		int result = customerService.insertCustomerComment(cc); //result가 1 이상이면?
		return "redirect:/customer/view?customerNo="+cc.getCustomerServiceRef();
	}
	
	@PostMapping(value="/deleteComment")
	@ResponseBody
	public String deleteComment(int commentNo) {
		int result = customerService.deleteComment(commentNo);
		
		if(result > 0) {
			return "success";
		}
		return "fail";
	}
	
	@PostMapping(value="/updateComment")
	public String updateComment(CustomerComment cc, @SessionAttribute(required = false) Member member) {
	    CustomerComment customerComment = customerService.selectOneComment(cc.getCommentNo()); 
	    
	    if (member != null && customerComment != null && member.getMemberNo() == customerComment.getCustomerWriterNo()) {
	        customerService.updateComment(cc);
	    }
	    return "redirect:/customer/view?customerNo=" + cc.getCustomerServiceRef();
	}

	@GetMapping(value="/deleteComment") 
	public String deleteComment(int customerServiceRef, int commentNo, @SessionAttribute(required = false) Member member) {
	    CustomerComment commentToDelete = customerService.selectOneComment(commentNo);

	    if(member != null && commentToDelete != null && member.getMemberNo() == commentToDelete.getCustomerWriterNo()){
	        customerService.deleteComment(commentNo);
	    }
	    return "redirect:/customer/view?customerNo=" + customerServiceRef;
	}
	
	@PostMapping(value="/updateStar")
	@ResponseBody
	public String updateStar(int customerNo, int customerStar, @SessionAttribute(required = false) Member member) {
		Customer c = customerService.selectOneCustomer(customerNo);
		if(member != null && c != null && member.getMemberNickname().equals(c.getCustomerNickname())) {
			Customer customerToUpdate = new Customer();
            customerToUpdate.setCustomerNo(customerNo);
            customerToUpdate.setCustomerStar(customerStar);
			
			int result = customerService.updateStarRating(customerToUpdate);
			if(result > 0) {
				return "success";
			}
		}
		return "fail";
	}
	
	@GetMapping(value="/filedown")
	public void filedown(int customerFileNo, HttpServletResponse response) {
		CustomerServiceFile customerFile = customerService.selectOneCustomerFile(customerFileNo);
		String savepath = root+"/customer/";
		
		fileUtil.downloadFile(savepath, customerFile.getFilePath(), customerFile.getFileName(), response);
	}
	
	@GetMapping(value="/updateFrm")
	public String updateFrm(int customerNo, Model model) {
		Customer c = customerService.selectOneCustomer(customerNo);
		model.addAttribute("c", c);
		
		return "customer/updateFrm";
	}
	
	@PostMapping(value="/update")
	public String update(Customer c, MultipartFile[] upfile, int[] delFileNo) {
		List<CustomerServiceFile> fileList = new ArrayList<CustomerServiceFile>();
		String savepath = root+"/customer/";
		if(!upfile[0].isEmpty()) {
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
				CustomerServiceFile csf = new CustomerServiceFile();
				csf.setFileName(filename);
				csf.setFilePath(filepath);
				fileList.add(csf);
			}
		}
		List<CustomerServiceFile> delFileList = customerService.updateCustomer(c,fileList,delFileNo);
		for(CustomerServiceFile csf : delFileList) {
			File delFile = new File(savepath+csf.getFilePath());
			delFile.delete();
		}
		return "redirect:/customer/view?customerNo="+c.getCustomerNo();
	}
}















