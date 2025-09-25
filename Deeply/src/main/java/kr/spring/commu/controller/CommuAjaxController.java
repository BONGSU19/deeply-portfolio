package kr.spring.commu.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.commu.service.CommuService;
import kr.spring.commu.vo.CommuReplyVO;
import kr.spring.commu.vo.CommuResponseVO;
import kr.spring.commu.vo.CommuVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/commu")
public class CommuAjaxController {
	@Autowired
	private CommuService commuService;
	
	/*===================
	 * 글 업로드 파일 삭제
	 *===================*/
	@GetMapping("/deleteFile")
	@ResponseBody
	public Map<String,String> processFile(long c_num,
			              @AuthenticationPrincipal
			              PrincipalDetails principal,
			              HttpServletRequest request){
		Map<String,String> mapJson = 
				new HashMap<String,String>();
		try {
			//로그인 정보
			MemberVO user = principal.getMemberVO();
			CommuVO db_commu = commuService.selectCommu(c_num);
			//로그인한 회원번호와 작성자 회원번호 일치 여부 체ㅡ
			if(user.getUser_num() != db_commu.getUser_num()) {
				//불일치
				mapJson.put("result", "wrongAccess");
			}else {
				//일치
				commuService.deleteFile(c_num);
				FileUtil.removeFile(request, db_commu.getC_file());
				mapJson.put("result", "success");
			}
			
		}catch(NullPointerException e) {
			mapJson.put("result", "logout");
		}		
		return mapJson;
	}
	/*===================
	 * 댓글 등록
	 *===================*/	
	@PostMapping("/writeReply")
	@ResponseBody
	public Map<String,String> writeReply(
			               CommuReplyVO commuReplyVO,
			               @AuthenticationPrincipal
			               PrincipalDetails principal,
			               HttpServletRequest request){
		log.debug("<<댓글 등록>> : " + commuReplyVO);
		
		Map<String,String> mapJson = 
				new HashMap<String,String>();
		try {
			//회원번호 저장
			commuReplyVO.setUser_num(
					principal.getMemberVO().getUser_num());
			//댓글 등록
			commuService.insertReply(commuReplyVO);
			mapJson.put("result", "success");
		}catch(NullPointerException e) {
			mapJson.put("result", "logout");
		}		
		return mapJson;
	}
	/*===================
	 * 댓글 목록
	 *===================*/	
	@GetMapping("/listReply")
	@ResponseBody
	public Map<String,Object> getList(
			        int c_num,int pageNum,
			        int rowCount, 
			        @AuthenticationPrincipal
			        PrincipalDetails principal){
		log.debug("<<c_num>> : " + c_num);
		log.debug("<<currentPage>> : " + pageNum);
		
		Map<String,Object> map = 
				new HashMap<String,Object>();
		map.put("c_num",c_num);
		
		//총글의 개수
		int count = commuService.selectRowCountReply(map);
		
		PagingUtil page = 
				new PagingUtil(pageNum,count,rowCount);
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		List<CommuReplyVO> list = null;
		if(count > 0) {
			list = commuService.selectListReply(map);
		}else {
			list = Collections.emptyList();
		}
		
		Map<String,Object> mapJson = 
				new HashMap<String,Object>();
		mapJson.put("count", count);
		mapJson.put("list", list);
		//로그인한 회원번호와 작성자 회원번호 일치 여부를 체크하기 위해
		//로그인한 회원번호 전송
		if(principal!=null) {
			mapJson.put("user_num", 
					principal.getMemberVO().getUser_num());
		}
		
		return mapJson;
	}
	/*===================
	 * 댓글 수정
	 *===================*/
	@PostMapping("/updateReply")
	@ResponseBody
	public Map<String,String> modifyReply(
			         CommuReplyVO CommuReplyVO,
			         @AuthenticationPrincipal
			         PrincipalDetails principal,
			         HttpServletRequest request){
		log.debug("<<댓글 수정>> : " + CommuReplyVO);
		
		Map<String,String> mapJson =
				new HashMap<String,String>();
		
		CommuReplyVO db_reply = 
				commuService.selectReply(
						CommuReplyVO.getCre_num());
		if(principal==null) {
			//로그인이 되지 않은 경우
			mapJson.put("result", "logout");
		}else if(principal.getMemberVO().getUser_num() == 
				                  db_reply.getUser_num()) {
			//로그인 회원번호와 작성자 회원번호 일치
			//댓글 수정
			commuService.updateReply(CommuReplyVO);
			mapJson.put("result", "success");
		}else {
			//로그인 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wrongAccess");
		}		
		return mapJson;
	}
	/*===================
	 * 댓글 삭제
	 *===================*/
	@GetMapping("/deleteReply")
	@ResponseBody
	public Map<String,String> deleteReply(
			       long cre_num,
			       @AuthenticationPrincipal
			       PrincipalDetails principal){
		log.debug("<<댓글 삭제 - re_num>> : " + cre_num);
		Map<String,String> mapJson =
				new HashMap<String,String>();
		CommuReplyVO db_reply = 
				commuService.selectReply(cre_num);
		if(principal==null) {
			//로그인이 되어있지 않음
			mapJson.put("result", "logout");
		}else if(principal.getMemberVO().getUser_num()
				                ==db_reply.getUser_num()) {
			//로그인한 회원번호와 작성자 회원번호 일치
			commuService.deleteReply(cre_num);
			mapJson.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wrongAccess");
		}		
		return mapJson;
	}
	
	/*===================
	 * 답글 등록
	 *===================*/	
	@PostMapping("/writeResponse")
	@ResponseBody
	public Map<String,String> writeResponse(
			     CommuResponseVO commuResponseVO,
			     @AuthenticationPrincipal
			     PrincipalDetails principal,
			     HttpServletRequest request){
		log.debug("<<답글 등록>> : " + commuResponseVO);
		
		Map<String,String> mapJson = new HashMap<String,String>();
		try {
			//회원번호 저장
			commuResponseVO.setUser_num(
					principal.getMemberVO().getUser_num());
			//답글 등록
			commuService.insertResponse(commuResponseVO);
			mapJson.put("result", "success");
		}catch(NullPointerException e) {
			mapJson.put("result", "logout");
		}		
		return mapJson;
	}
	/*===================
	 * 답글 목록
	 *===================*/		
	@GetMapping("/listResp")
	@ResponseBody
	public Map<String,Object> getListResp(long cre_num,
			                  @AuthenticationPrincipal
			                  PrincipalDetails principal){
		log.debug("<<cre_num>> : " + cre_num);
		
		List<CommuResponseVO> list = 
				commuService.selectListResponse(cre_num);
		Map<String,Object> mapJson = 
				new HashMap<String,Object>();
		mapJson.put("list", list);
		if(principal!=null) {
			mapJson.put("user_num", 
					principal.getMemberVO().getUser_num());
		}		
		return mapJson;
	}
	/*===================
	 * 답글 수정
	 *===================*/
	@PostMapping("/updateResponse")
	@ResponseBody
	public Map<String,String> modifyResponse(
					CommuResponseVO commuResponseVO,
			        HttpServletRequest request,
			        @AuthenticationPrincipal
			        PrincipalDetails principal){
		log.debug("<<답글 수정>> : " + commuResponseVO);
		
		Map<String,String> mapJson = 
				new HashMap<String,String>();
		
		CommuResponseVO db_response = 
				commuService.selectResponse(
						commuResponseVO.getPe_num());
		if(principal==null) {
			//로그인이 되지 않은 경우
			mapJson.put("result", "logout");
		}else if(principal.getMemberVO().getUser_num()==
				       db_response.getUser_num()) {
			//로그인 회원번호와 작성자 회원번호 일치
			//답글 수정
			commuService.updateResponse(commuResponseVO);
			mapJson.put("result", "success");
		}else {
			//로그인 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wrongAccess");
		}
		
		return mapJson;
	}
	/*===================
	 * 답글 삭제
	 *===================*/
	@PostMapping("/deleteResponse")
	@ResponseBody
	public Map<String,Object> deleteResponse(
			             long pe_num,int user_num,
			             @AuthenticationPrincipal
			             PrincipalDetails principal){
		log.debug("<<답글 삭제 - pe_num>> : " + pe_num);
		log.debug("<<답글 삭제 - user_num>> : " + user_num);
		
		Map<String,Object> mapJson = 
				new HashMap<String,Object>();
		if(principal==null) {
			//록그인이 되지 않은 경우
			mapJson.put("result", "logout");
		}else if(principal.getMemberVO().getUser_num()==user_num) {
			//로그인 회원번호와 작성자 회원번호 일치
			CommuResponseVO response = 
					commuService.selectResponse(pe_num);
			commuService.deleteResponse(pe_num);
			int cnt = commuService.selectResponseCount(
					                  response.getCre_num());
			log.debug("<<답글 삭제 후 나머지 답글 개수>> : " + cnt);
			mapJson.put("cnt", cnt);
			mapJson.put("result", "success");
		}else {
			//로그인 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "success");
		}		
		return mapJson;
	}
}
