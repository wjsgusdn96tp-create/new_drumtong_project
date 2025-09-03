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
	
    // 인증번호 발송
    @ResponseBody
    @PostMapping("/sendMail")
    public int sendMail(@RequestParam String memberEmail, HttpSession session) {
        String code = String.format("%06d", new Random().nextInt(1_000_000));
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(memberEmail);
        msg.setSubject("Drum Tong 회원가입 인증번호");
        msg.setText("인증번호는 " + code + " 입니다.");
        try {
            mailSender.send(msg);
            session.setAttribute("verificationCode", code);
            return 1; // 성공
        } catch (Exception e) {
            return 0; // 실패
        }
    }

    // 인증번호 확인
    @ResponseBody
    @PostMapping("/verifyCode")
    public int verifyCode(@RequestParam String code, HttpSession session) {
        String saved = (String) session.getAttribute("verificationCode");
        if (saved != null && saved.equals(code)) {
            session.setAttribute("emailVerified", true);
            return 1; // 인증 성공
        }
        return 0; // 인증 실패
    }
}
