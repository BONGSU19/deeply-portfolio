package kr.spring.commu.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import kr.spring.follow.vo.FollowVO;
import kr.spring.member.vo.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommuVO {
	private Long c_num;
	private int c_category;
	private String c_title;
	private String c_content;
	private Date c_date;
	private Date c_update;
	private int c_hit;
	private String c_file;
	
	private MultipartFile upload;	//파일
	
	//작성자 정보
	private Long user_num;
	private String nick_name;
	private MemberVO memberVO;
	
	//팔로우 정보
	private FollowVO followVO;
	
	//팬덤 내 상세 카테고리
	private Long c_fandom; //DB저장
	private String arti_name;
	
	private int cre_cnt;
}