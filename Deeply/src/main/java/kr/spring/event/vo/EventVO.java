package kr.spring.event.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EventVO {
	private long perf_num;//공연 번호
	@NotNull
	private long hall_num;//공연장 번호
	private String hall_name;//공연장 이름
	private String location;//공연 장소
	private int booked_amount;//예매 수
	private long artist_num;//아티스트(그룹) 번호
	private String group_name;//아티스트(그룹) 이름
	@NotBlank
	private String mem_date;//멤버십 선예매 날짜
	@NotBlank
	private String perf_date;//공연 날짜
	private String end_date;//공연 종료날짜
	@NotBlank
	private String perf_time;//공연 시간
	@NotBlank
	private String end_time;//끝나는 시간
	private String perf_status;//공연 상태(진행중, 완료 ,,)
	@NotBlank
	private String perf_title;//공연 제목
	@NotEmpty
	private String perf_desc;//공연 설명
	@NotNull
	private int ticket_price;//티켓 가격
	@NotBlank
	private String book_date;//예매 시작날짜
	@NotBlank
	private String booking_deadline;//예매 종료날짜
	private String perf_photo;
	private MultipartFile upload;
	private String reg_date;
	private int isMembership;
	private String status_name;
	
}
