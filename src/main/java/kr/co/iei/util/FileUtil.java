package kr.co.iei.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FileUtil {
	public String upload(String savepath, MultipartFile file) {

		String filename = file.getOriginalFilename(); //원본파일명 추출
		String onlyFilename = filename.substring(0, filename.lastIndexOf(".")); // 확장자 뺀 파일명
		String extention = filename.substring(filename.lastIndexOf(".")); // 확장자
		
		String filepath = null;
		int count = 0; // 파일중복 확인 후 붙일 변수
		while(true) {
			if(count == 0) { // 파일명 만들기
				filepath = onlyFilename+extention; 
			} else {
				filepath = onlyFilename+"_"+count+extention;
			}
			File checkFile = new File(savepath+filepath);
			if(!checkFile.exists()) {  // 기존 파일과 비교해서 없으면 반복문 종료
				break;
			}
			count++;			
		}		

		File uploadFile = new File(savepath+filepath);
		try {
			file.transferTo(uploadFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filepath;
	}
	
	public void downloadFile(String savepath, String filepath, String filename, HttpServletResponse response) {
		String downFile = savepath+filepath;
		
		try {
			FileInputStream fis = new FileInputStream(downFile); // 첨부파일 주스트림
			BufferedInputStream bis = new BufferedInputStream(fis); // 첨부파일 보조스트림
			try {
				ServletOutputStream sos = response.getOutputStream(); // 사용자에게 보낼 주스트림
				BufferedOutputStream bos = new BufferedOutputStream(sos); // 보조스트림
				String resFilename = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
				
				
				response.setContentType("application/octet-stream"); // HTTP Header설정(응답형식/파일이름)
				response.setHeader("Content-Disposition", "attachment;filename="+resFilename);
				
				while(true) { // 파일 읽어서 전송
					int read = bis.read();
					if(read == -1) {
						break;
					}
					bos.write(read);					
				}
				bos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
