package kr.spring.member.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"photo"})
	public class ArtistVO {
	    private Long user_num;         // 유저 번호 (PK, FK로 사용될 시퀀스)
	    @NotBlank(message = "그룹 이름은 필수 입력 값입니다.")
	    private String group_name;     // 그룹 이름
	    @NotBlank(message = "이름은 필수 입력 값입니다.")
	    private String name;  

	    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*()_+]{8,20}$", message = "비밀번호는 8~20자의 영문자, 숫자, 특수문자로 구성되어야 합니다.")// 아티스트 이름
	    private String passwd_hash;    // 비밀번호 해시
	    // 프로필 사진 이름 지정
	    @NotBlank(message = "코드는 필수 입력 값입니다.")
	    private String code;
	    @NotBlank(message = "이메일은 필수 입력 값입니다.")
	    @Email(message = "유효한 이메일 형식이 아닙니다.")
	    private String email;
	    @NotBlank(message = "아이디는 필수 입력 값입니다.")
	    private String id;
	    @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
	    private String confirmPassword;
		private Date debut_date;
	    private String intro;
	    private String Photo_name;
		private byte[] photo;
		
		private int follow_cnt;
		private Long follow_num;
		private Long follower_num;
		
		public void setUpload(MultipartFile upload) 
	            throws IOException {
	//MultipartFile -> byte[]
	setPhoto(upload.getBytes());
	//파일 이름
	setPhoto_name(upload.getOriginalFilename());
	}
}

