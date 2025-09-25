package kr.spring.payment.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.payment.service.FanService;
import kr.spring.payment.vo.FanVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/fan")
public class FanController {
	@Autowired
	private FanService fanService;
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/selectArtist")
    public String selectArtist(@RequestParam("user_num") Long user_num, 
                               @AuthenticationPrincipal PrincipalDetails principal, 
                               Model model) {
        // 내 정보 가져오기
        Long userNum = principal.getMemberVO().getUser_num();
        Long user_bal = fanService.getUser_bal(userNum);
        log.debug("내 정보: " + userNum);
        log.debug("예치금: " + user_bal);

        // 아티스트 정보 저장
        ArtistVO artist = fanService.artiInfo(user_num);
        FanVO fan = fanService.selectArtist(user_num);
        FanVO db_fan = fanService.selectFan(userNum, user_num);
        log.debug("artist 정보: " + artist);

        // 팬 가입, 자동 결제일 계산
        LocalDate fan_start = LocalDate.now();
        LocalDate fan_end = LocalDate.now().plusMonths(1);

        // 모델에 데이터 저장
        model.addAttribute("userNum", userNum);
        model.addAttribute("user_bal", user_bal);
        model.addAttribute("artist", artist);
        model.addAttribute("fan", fan);
        model.addAttribute("db_fan", db_fan);
        model.addAttribute("fan_start", fan_start);
        model.addAttribute("fan_end", fan_end);

        // artistFanJoin.jsp로 이동
        return "payment/artistFanJoin";
    }
    
    //팬 가입처리
    @PostMapping("/joinFan")
    @ResponseBody
    public Map<String, Object> joinFan(FanVO db_fan, @RequestParam Long user_num,
                                                        @RequestParam Long fan_artist) {
        Map<String, Object> mapJson = new HashMap<>();

        try {
        	Long user_bal = fanService.getUser_bal(user_num);
            
        	// 1.기존 가입회원인지 확인
        	FanVO fan = fanService.selectFan(user_num, fan_artist);
        	if (fan != null) {
        		mapJson.put("result", "used");
        		return mapJson; // 기존 회원일 시 실패 응답 반환
        	}
        	
        	// 1. 잔액 확인
            if (user_bal < 6500) {
            	mapJson.put("result", "noMoney");
                return mapJson;  // 잔액 부족 시 실패 응답 반환
            }
            
            // FanVO 객체에 값 설정
            db_fan.setFan_artist(fan_artist); // artistNum은 아티스트의 user_num
            db_fan.setUser_num(user_num); // userNum은 현재 사용자 번호

            // 서비스 호출
            fanService.joinFan(db_fan);
            fanService.updateUser_bal(user_num);

            mapJson.put("result", "success");
        }catch(NullPointerException e) {
			mapJson.put("result", "logout");
		}
        
        return mapJson;
    }
    
    //탈퇴신청
    @PostMapping("/removeFan")
    @ResponseBody
    public Map<String,Object> removeFan(FanVO fan, @RequestParam Long user_num,
                                        @RequestParam Long fan_artist,
                                        @RequestParam int fan_status){
    	
    	Map<String,Object> mapJson = new HashMap<String,Object>();
    	
    	//팬 회원인지 확인
    	FanVO db_fan = fanService.selectFan(user_num,fan_artist);
    	
    	if(db_fan == null){
    		mapJson.put("result","noFan");
    	}else if(db_fan != null){
    		//서비스 호출
    		fanService.removeFan(db_fan);
    		
    		mapJson.put("result","success");
    	}
    	return mapJson;
    }
    
    //즉시탈퇴
    @PostMapping("/deleteFan")
    @ResponseBody
    public Map<String,Object> deleteNowFan(@RequestParam Long user_num,
            @RequestParam Long fan_artist){
    	
    	//DB에 저장된 만기된 팬 정보 구하기
    	FanVO db_fan = fanService.noMoreFan(user_num, fan_artist);
    	
    	Map<String,Object> mapJson = new HashMap<String,Object>();
    	
    	if(db_fan == null){
    		mapJson.put("result","noFan");
    	}else if(db_fan != null){
    		//서비스 호출
    		fanService.deleteFan(db_fan);
    		
    		mapJson.put("result","success");
    	}
    	return mapJson;
    }
    
    
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myArtistList")
	public String getMyArtist(@RequestParam(defaultValue="1")int pageNum,
			@AuthenticationPrincipal 
			PrincipalDetails principal,
			Model model, FanVO fan) {

		//로그인한 회원번호 저장
		Long user_num = principal.getMemberVO().getUser_num();

		//전체 팬맺은 아티스트 수
		int count = fanService.countMyArtist(user_num);

		//페이지 처리
		PagingUtil page = new PagingUtil(null,null,pageNum, count, 20, 5, "myArtistList");

		List<FanVO> myArtistList = null;

		if(count > 0) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("user_num", user_num);
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());

			//아티스트 리스트 가져오기
			myArtistList = fanService.getMyArtist(map);
		}

		model.addAttribute("count",count);
		model.addAttribute("myArtistList",myArtistList);

		return "/payment/myArtistList";
	};
}
