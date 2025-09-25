package kr.spring.main.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/common")
public class CommonController {
	//인증 관련 오류 페이지 호출
	@GetMapping("/accessDenied")
	public String getAccessDenied() {
		return "common/accessDenied";
	}
	//인증 관련 페이지 호출(JSON)
	@GetMapping("/accessLogout")
	public String getAccessLogout() {
		return "common/accessLogout";
	}//파일 최대 업로드 용량 초과
	@GetMapping("/fileSizeExceeded")
	public String getFileSizeExceeded() {
		return "/common/fileSizeExceede";
	}
	@PostMapping("/imageUploader")
	@ResponseBody
	public Map<String,Object> uploadImage(MultipartFile upload,
			HttpServletRequest request,
			HttpServletResponse response,
			@AuthenticationPrincipal
			PrincipalDetails principal) throws Exception {
		// 업로드할 폴더 경로
		String realFolder = request.getServletContext().getRealPath("/assets/image_upload");

		// 업로드할 파일 이름
		String org_filename = upload.getOriginalFilename();
		//특수문자 제거, 공백은 _ 처리 후 파일명 앞에 시간을 long 타입으로 반환한 데이터를 결합
		String str_filename = System.currentTimeMillis() +"_"+ org_filename.replaceAll("[\\{\\}\\[\\]\\/?,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", "").replaceAll("\\s","_");

		log.debug("<<원본 파일명>> : " + org_filename);
		log.debug("<<저장할 파일명>> : " + str_filename);

		Map<String,Object> map = new HashMap<String,Object>();

		String sub_path;//중간 경로
		if(principal != null) {
			MemberVO user = (MemberVO)principal.getMemberVO();
			ArtistVO artist = (ArtistVO)principal.getArtistVO();
			log.debug("<<로그인>>");
			if(user!=null) {
				sub_path = String.valueOf(user.getUser_num());
			}else {
				sub_path = String.valueOf(artist.getUser_num());
			}
			String file_path = realFolder + "/" + sub_path + "/" + str_filename;
			log.debug("<<파일경로>> : " + file_path);

			File f = new File(file_path);
			if (!f.exists()) {
				//상위 경로를 생성해야 하기 때문에 mkdirs()사용할 것 mkdir()를 사용하는 상위 경로를 만들지 못 함
				f.mkdirs();
			}
			upload.transferTo(f);

			map.put("uploaded", true);
			map.put("url", request.getContextPath()+"/assets/image_upload/"+sub_path+"/"+str_filename);
		}else {
			log.debug("<<로그아웃>>");
			map.put("uploaded", false);
		}
		return map;
	}
}







