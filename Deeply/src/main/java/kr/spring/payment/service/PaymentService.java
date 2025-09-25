package kr.spring.payment.service;



import kr.spring.payment.vo.PaymentVO;

public interface PaymentService {
	public void insertOrder(PaymentVO payment);
	public void updateBal(PaymentVO payment);

}
