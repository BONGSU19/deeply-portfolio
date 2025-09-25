package kr.spring.booking.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookingVO {
	private Long booking_num;
	private long perf_num;
	private long user_num;
	@Min(value = 1, message = "예약 인원은 최소 1명 이상이어야 합니다.")
	@Max(value = 2, message = "예약 인원은 최대 2명 입니다.")
	private int booked_seat;
	private int total_price;
	private String booking_date;
	@NotBlank
	private String seat_num1;
	private String seat_num2;
	@NotBlank
	private String name;
	private String name2;
	@Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다.")
	@NotBlank
	private String phone;
	private String phone2;
	private String more_info;
	@NotBlank
	private String deliver_name;
	@NotBlank
	private String zipcode;
	@NotBlank
	private String address1;
	private String address2;
	private String request;
	private int isMembership;
	private String perf_title;
	private String group_name;
	private String perf_photo;
}
