package kr.spring.item.vo;

import java.sql.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
public class CartVO {
	private long cart_num;
	private long item_num;
	private long user_num;
	private Date reg_date;
	private int order_quantity;//장바구니에 담겨있는 주문 할 상품 개수
	
	private String item_name;
	@Min(value = 1, message = "구매개수는 최소 1개 이상이어야 합니다.")
	@Max(value = 2, message = "구매개수는 최대 3개 입니다.")
	private int item_quantity; //결제까지 마친 주문된 상품의 개수
	private int total_price;
	private int item_price;
	private MultipartFile upload;				//파일
	private String filename;					//파일명
	private int isPremium;
	private int isMember;
}
