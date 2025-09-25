package kr.spring.commu.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommuResponseVO {
	private long pe_num;
	private String pe_content;
	private long pe_parent_num;
	private int pe_depth;
	private String pe_date;
	private String pe_update;
	private long cre_num;
	private long user_num;
	
	private String id;
	private String nick_name;
	private String parent_id;
	private String pnick_name;	
}
