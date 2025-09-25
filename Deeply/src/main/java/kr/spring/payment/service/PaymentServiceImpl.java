package kr.spring.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import kr.spring.payment.dao.PaymentMapper;

import kr.spring.payment.vo.PaymentVO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{
	@Autowired
	PaymentMapper paymentMapper;



	@Override
	public void insertOrder(PaymentVO payment) {
		long pay_num = paymentMapper.paymentNum(); 
        payment.setPAY_NUM(pay_num);
		paymentMapper.insertOrder(payment);
	}



	@Override
	public void updateBal(PaymentVO payment) {
		paymentMapper.updateBal(payment);
		
	}
	
	}
