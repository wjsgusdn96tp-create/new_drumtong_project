package kr.co.iei.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	@Autowired
	private JavaMailSender sender;

	public void send(SimpleMailMessage message) {
	    try {
	        sender.send(message);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("메일 전송 실패: " + e.getMessage());
	    }
	}
}
