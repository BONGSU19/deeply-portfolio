package kr.spring.videocomment.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.vo.PrincipalDetails;
import kr.spring.videocomment.service.VideoCommentService;
import kr.spring.videocomment.vo.VideoCommentVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/videos")
public class VideoCommentController {
    
    @Autowired
    private VideoCommentService videoCommentService;

    /*===================
     * 댓글 작성
     *===================*/	
    @PostMapping("/writeReply")
    @ResponseBody
    public Map<String, String> writeReply(
            VideoCommentVO videoCommentVO,
            @AuthenticationPrincipal PrincipalDetails principal,
            HttpServletRequest request) {
        
        log.debug("<<댓글 작성>> : " + videoCommentVO);
        
        Map<String, String> mapJson = new HashMap<String, String>();
        
        try {
			//회원번호 저장
			videoCommentVO.setUserNum(principal.getArtistVO().getUser_num());
			//댓글 등록
			videoCommentService.insertComment(videoCommentVO);
			mapJson.put("result", "success");
		}catch(NullPointerException e) {
			mapJson.put("result", "logout");
		}		
		return mapJson;
    }
    
    /*===========================
     * 2) 댓글 목록 가져오기
     *===========================*/
    @GetMapping("/commentList")
    @ResponseBody
    public Map<String,Object> getCommentList(
            @RequestParam("videoId") Long videoId) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 해당 영상의 댓글 목록 조회
            List<VideoCommentVO> list = videoCommentService.selectCommentsByVideoId(videoId);
            
            log.debug("댓글 조회 결과 : {}", list);

            map.put("result", "success");
            map.put("comments", list);
        } catch(Exception e) {
            log.error("댓글 목록 조회 에러", e);
            map.put("result", "error");
        }
        return map;
    }
}
