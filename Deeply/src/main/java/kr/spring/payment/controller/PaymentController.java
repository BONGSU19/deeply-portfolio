package kr.spring.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import kr.spring.booking.vo.BookingVO;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.payment.service.PaymentService;
import kr.spring.payment.vo.PaymentCompletionRequest;
import kr.spring.payment.vo.PaymentVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/charge")
public class PaymentController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/payment")
	public String paymentMain(@RequestParam("pay_price") int payPrice ,
							  @RequestParam(required=false) Long booking_num,
							  @RequestParam(required=false) Long order_num,
							  @RequestParam(required=false) Integer item_quantity,
			@AuthenticationPrincipal PrincipalDetails principal,Model model) {
	 MemberVO memberVO = principal.getMemberVO();
		model.addAttribute("memberVO", memberVO);
		model.addAttribute("payPrice", payPrice);
		if(booking_num != null) {
			model.addAttribute("booking_num", booking_num);
		}
		if(order_num != null) {
			model.addAttribute("order_num", order_num);
			model.addAttribute("item_quantity",item_quantity);
		}
		return "payment";
	}
	
	
	  @PostMapping("/complete")
	    public ResponseEntity<String> completePayment(@RequestBody PaymentCompletionRequest request) {
	        try {
	            // 결제 정보 객체 생성
	            PaymentVO payment = new PaymentVO();
	           
	            payment.setTotalAmount(request.getTotalAmount());
	            payment.setUSER_NUM(request.getUser_num());
	            paymentService.updateBal(payment);
	            // 결제 처리 서비스 호출
	            paymentService.insertOrder(payment);
	            
	           
	            return ResponseEntity.ok("결제 완료 처리 성공");
	        } catch (Exception e) {
	        	  e.printStackTrace(); // 예외 출력
	        	  return ResponseEntity.status(500).body("결제 처리 실패: " + e.getMessage());
	        }
	    }
	
	@GetMapping("/userBal")
	public String userBal(@AuthenticationPrincipal PrincipalDetails principal,Model model) {
		 	MemberVO memberVO = principal.getMemberVO();
		 	 int userBal = memberVO.getUser_bal();
			model.addAttribute("memberVO", memberVO);
			model.addAttribute("user_bal",userBal);
			log.debug("User balance: " + userBal);
		return "userBal";
	}
	

}
