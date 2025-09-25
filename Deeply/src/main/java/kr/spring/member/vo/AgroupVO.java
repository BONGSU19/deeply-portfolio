package kr.spring.member.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AgroupVO {
	private long group_num;
	private String group_name;
	private MultipartFile upload;
	private String group_photo;
	private String fandom_name;
	private String intro_desc;
}
