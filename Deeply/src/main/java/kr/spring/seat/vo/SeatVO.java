package kr.spring.seat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SeatVO {
	private String seat_num;
	private long hall_num;
	private String srow;
	private String scolumn;
	private String seat_type;
	private String status;
	private int price;
}
