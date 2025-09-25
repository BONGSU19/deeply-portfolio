package kr.spring.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.spring.payment.service.FanService;
import kr.spring.payment.vo.FanVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FanSchedulerApp {
	@Autowired
	private FanService fanService;
	/*
	Cron 표현식			의미
	0 10 * * * ?		매 시각 10분
	0 0/10 * * * ?		매 10분마다
	0 0 12 * * ?		매일 오후 12시
	0 15 10 * * ?		매일 오전 10시 15분
	0 15 10 * * ? 2021	2021년 동안 매일 오전 10시 15분
	0 * 14 * * ?		매일 오후 2시부터 2시 59분까지 1분마다
	0 0/5 14 * * ?		매일 오후 2시부터 2시 55분까지 5분마다
	0 0/5 14, 18 * * ?	매일 오후 2시부터 2시 55분까지 5분마다,
						매일 오후 6시부터 6시 55분까지 5분마다
	*/
	//첫번째 부터 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
	@Scheduled(cron = "0 0 1 * * ?") 
	//정기 결제
    public void regularService() {
		log.debug("<<정기 결제 실행>>");
		FanVO fan = new FanVO();
		//돈없어서 팬 해지 목록
    	List<FanVO> noMoneyFans = fanService.selectNoMoney(fan);
    	//돈있어서 팬 유지 목록
    	List<FanVO> keepFans = fanService.selectKeepFan(fan);
    	
    	//예치금 유무에 따라서 팬 자동 처리
    	for (FanVO noMoneyfan : noMoneyFans) {
            fanService.noMoney(noMoneyfan);
        }
        for (FanVO keepFan : keepFans) {
            fanService.keepFan(keepFan);
        }
        
        //만기일되어서 탈퇴 완료 : 조건 체크해서 삭제
        fanService.deleteFan(fan);
    }
	
}
