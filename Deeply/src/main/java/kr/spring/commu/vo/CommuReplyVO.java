package kr.spring.commu.vo;

import java.sql.Date;

import kr.spring.member.vo.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommuReplyVO {
	private Long cre_num;
	private String cre_content;
	private Date cre_date;
	private Date cre_update;
	private String cre_file;
	private Long user_num;

	private Long cre_refe_num;
	private Long c_num;
	
	private MemberVO memberVO;
}