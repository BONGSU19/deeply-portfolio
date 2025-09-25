package kr.spring.payment.vo;

import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
public class PaymentCompletionRequest {

    private int totalAmount;     // 결제 금액

    private int user_num;   // 고객 ID   // 기타 필요한 필드들
    
    private long booking_num;
    
    private long order_num;
    
    private int item_quantity;
}
