package kr.spring.follow.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FollowVO {
	private Long follower_num;
	private Long follow_num;
	private Date follow_date;
	
	//팔로우 수
	private Long follow_cnt;
	//팔로워 수
	private Long follower_cnt;
	
	//컬럼에는 없지만 저장하는 것
	private String name;
	private String group_name;
	private Long user_num;
}
