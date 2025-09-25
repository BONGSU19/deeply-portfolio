package kr.spring.payment.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVO {
    private long PAY_NUM;    // 결제 고유 ID
    private int totalAmount;     // 결제 금액
    private String status;       // 결제 상태 (예: 성공, 실패)
    private int USER_NUM;   // 고객 ID   // 기타 필요한 필드들
}
