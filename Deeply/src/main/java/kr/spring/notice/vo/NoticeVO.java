package kr.spring.notice.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import kr.spring.follow.vo.FollowVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeVO {
	private Long notice_num;
	private String notice_title;
	private String notice_content;
	private String notice_file;
	private Long user_num;
	private Date notice_date;
	private Date notice_update;
	
	private MultipartFile upload;	//파일
	
	FollowVO follow;
	private Long fan_artist;
	
	private String group_name;
	private String name;
	
}
