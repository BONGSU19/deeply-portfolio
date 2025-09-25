package kr.spring.chat.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatVO {
	
	//공통의 정보(중간 테이블 정보)
	private long chat_user_num; //채팅에 참여하는 유저
	private long chat_num; //채팅방 번호 
	private String chat_id; //채팅에 참여하는 아이디
	private int chat_kind; // 참여하는 유저 종류 (0이면 유료, 1이면 아티스트)
		
	
	//채팅방 정보
	
	private String chat_name; //채팅방 이름
	private int chat_status; //채팅방 상태(on : 1/off : 0)

	//채팅에 참여하는 아티스트 정보
	private long auser_num; //아티스트 번호
	private String auser_id; //아티스트 아이디
	
	//채팅에 참여하는 유저
	private long duser_num; //아티스트 번호
	private String duser_id; //아티스트 아이디
	
	//아티스트 말풍선 정보
	private String ballon_name;
	
	private String chatName;   // 채팅방 이름
    private String ballonName; // 말풍선 내용
    private MultipartFile artistPhoto; // 아티스트 사진 (파일 업로드 처리)
	
	

	
}
