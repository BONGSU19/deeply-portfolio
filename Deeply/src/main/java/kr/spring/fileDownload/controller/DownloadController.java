package kr.spring.fileDownload.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.spring.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/download")
public class DownloadController {
	@GetMapping("/file")
	public String download(String file_name,
			    HttpServletRequest request,
			     Model model) {
		byte[] downloadFile = 
				FileUtil.getBytes(
						request.getServletContext()
						       .getRealPath("/assets/upload")
						            +"/"+file_name);
		
		model.addAttribute("downloadFile", downloadFile);
		model.addAttribute("filename", file_name);
		
		return "downloadView";
	}
}
