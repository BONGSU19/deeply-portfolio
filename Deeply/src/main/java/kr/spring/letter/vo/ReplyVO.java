package kr.spring.letter.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyVO {
	private long reply_num;
	private long user_num;
	private long letter_num;
	private long artist_num;
	private String letter_title;
	private String letter_content;
	private String post_date;
	private String img;
	private MultipartFile upload;
	private String nick_name;
	private String file_name;
	private List<MultipartFile> file_upload;
}
