package kr.spring.item.vo;


import java.sql.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@ToString
public class OrderVO {
	private long order_num;
	private long item_num;
	private long user_num;
	@Min(value = 1, message = "구매개수는 최소 1개 이상이어야 합니다.")
	@Max(value = 3, message = "구매개수는 최대 3개 입니다.")
	private int item_quantity;//결제한 상품별 개수
	private int total_price;//결제한 총 금액
	private Date order_date;
	private long pay_num;
	private int item_status; //1:결제 및 주문완료 2:배송 준비중 3: 배송 중 4: 배송완료 5: 주문 취소 요청 6: 주문 취소 완료
	private String order_notice;
	private String item_name;
	private int item_price;//개별 상품 금액
	private String user_name;//구매자이름
	
	
	@NotBlank
	private String name;
	@Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다.")
	@NotBlank
	private String phone;
	@NotBlank
	private String zipcode;
	@NotBlank
	private String address1;
	private String address2;
	private String filename;
	
	
	
	

}
