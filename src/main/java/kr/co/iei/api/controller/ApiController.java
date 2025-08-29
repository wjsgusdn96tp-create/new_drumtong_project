package kr.co.iei.api.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.util.EmailSender;

@Controller
@RequestMapping(value="/api")
public class ApiController {
	
	@Autowired
	private EmailSender mailSender;
	
	// 인증코드 발송
	@ResponseBody
    @PostMapping("/sendMail")
    public String sendMail(@RequestParam String memberEmail, HttpSession session) {
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(memberEmail);
        message.setSubject("DrumTong 회원가입 인증번호");
        message.setText("인증번호는 " + verificationCode + " 입니다.");
        try {
            mailSender.send(message);
            session.setAttribute("verificationCode", verificationCode);
            return "이메일로 인증번호가 발송되었습니다.";
        } catch (Exception e) {
            e.printStackTrace();   // 콘솔에 예외 확인
            return "메일 전송 실패: " + e.getMessage();
        }
    }
    
    // 인증코드 확인
	@ResponseBody
    @PostMapping("/verifyCode")
    public String verifyCode(@RequestParam String code, HttpSession session) {
        String savedCode = (String) session.getAttribute("verificationCode");
        session.setAttribute("emailVerified", true); // 세션에 emailVerified 값을 넣기위해
        if (savedCode != null && savedCode.equals(code)) {
            session.setAttribute("isVerified", true); // 인증 성공 플래그
            return "인증 성공";  // AJAX가 바로 받는 메시지
        } else {
            return "인증 실패";  // AJAX가 바로 받는 메시지
        }
    }
}
