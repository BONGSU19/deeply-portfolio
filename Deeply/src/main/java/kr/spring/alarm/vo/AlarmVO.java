package kr.spring.alarm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlarmVO {
	
	private long al_num; //알람 번호
	private long user_num; //유저번호
	private String al_title; //알람 제목(e.g. 굿즈라면 굿즈 제목 등)
	private int al_kind; //알람 종류
	private int al_status ; //알람을 읽었는지에 대한 유무
	private long artist_num;

}
