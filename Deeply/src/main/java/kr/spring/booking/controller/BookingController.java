package kr.spring.booking.controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.member.service.ArtistService;
import kr.spring.booking.service.BookingService;
import kr.spring.booking.vo.BookingVO;
import kr.spring.event.vo.EventVO;
import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.payment.service.PaymentService;
import kr.spring.payment.vo.PaymentCompletionRequest;
import kr.spring.payment.vo.PaymentVO;
import kr.spring.seat.vo.SeatVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequestMapping("/booking")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private PaymentService paymentService;
	
	//아티스트 예매 목록
	@GetMapping("/list")
	public String getList(long group_num, @RequestParam(defaultValue="1") int pageNum,
										   @RequestParam(required=false) String status, 
										   @RequestParam(required=false) String dateRange, HttpServletRequest request, Model model,
										   @AuthenticationPrincipal PrincipalDetails principal) {
		
		
		log.debug("<<예매 페이지 아티스트 번호>> : " + group_num);
		log.debug("<<예매 페이지 번호>> : " + pageNum);
		Map<String,Object> map = new HashMap<String,Object>();
		
		AgroupVO group = artistService.selectArtistDetail(group_num);
		String group_name = group.getGroup_name();
		
		
		LocalDate today = LocalDate.now();
	    LocalDate hundredDaysLater = today.plusDays(100);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    String startDate = today.format(formatter);
	    String endDate = hundredDaysLater.format(formatter);
		
		if(dateRange !=  null) {
			String[] dates = dateRange.split(" ~ ");
			startDate = dates[0];
			endDate = dates[1];
		}else {
			dateRange = startDate + " ~ " + endDate;
			log.debug("Default dateRange : " + dateRange);
		}
		
		log.debug("dateRange: " + dateRange);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("user_num", group_num);
		if (status == null || "all".equals(status)) {
			status = null; // 쿼리에서 필터링되지 않도록 처리
		} else {
			map.put("status", status);
		}
		
		
		int count = bookingService.selectEventRowCount(map);
		log.debug("<<예매 페이지 count>> : " + count);
		PagingUtil page = new PagingUtil(pageNum, count,20,10,"list");
		
		List<EventVO> list = null;
		if(count > 0) {
			
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			
			list = bookingService.selectEventByArtistId(map);
			int isFan = 0;
			
			for(EventVO e : list) {
				if(e.getPerf_status().equals("before")) {
					e.setStatus_name("예매 전");
				}else if(e.getPerf_status().equals("ongoing")) {
					e.setStatus_name("예매 기간");
				}else if(e.getPerf_status().equals("membership")) {
					e.setStatus_name("선예매 기간");
				}else {
					e.setStatus_name("종료된 이벤트");
				}
				
			}
			
			if(isFan > 0) {
				for(EventVO e : list) {
					if(e.getPerf_status().equals("membership")) {
						e.setIsMembership(1);
					}
						
				}
			}
			
			
			
			if(principal.getMemberVO() != null) {
				MemberVO member = principal.getMemberVO();
				Map<String,Object> fanMap = new HashMap<String,Object>();
				fanMap.put("user_num", member.getUser_num());
				fanMap.put("group_name", group_name);
				isFan = bookingService.checkIfGroupMembership(fanMap);
				
				model.addAttribute("name", member.getName());
				model.addAttribute("address1", member.getAddress1());
				model.addAttribute("address2", member.getAddress2());
				model.addAttribute("zipcode", member.getZipcode());
				
				model.addAttribute("user_num", member.getUser_num());
				
			}
		}
		
		
		
		
	
		
		model.addAttribute("dateRange",dateRange);
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());
		model.addAttribute("artist_num", group_num);
		model.addAttribute("group_name", group_name);
		
		
		
		
		return "bookingList";
	}
	
	//예매 폼 
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/book")
	public String form(BookingVO bookingVO, long perf_num, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
	    log.debug("<<예매 작성폼>>");
	    
	    if(bookingVO == null) {
	    	bookingVO = new BookingVO();
	    	
	    }
	    
	    MemberVO member = principal.getMemberVO();
	    
	    if(principal.getArtistVO() != null) {
	    	AgroupVO av = principal.getAgroupVO();
	    	log.debug("------------------------------------------------" + av);
	    }
	    
	    
	    
	    loadBookingContents(perf_num, model);
	    bookingVO.setBooked_seat(1);
	    
	    
	    model.addAttribute("perf_num", perf_num);
	    model.addAttribute("member", member);
	    
	    
	    return "bookingForm";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/book")
	public String bookForm(@ModelAttribute @Valid BookingVO bookingVO,BindingResult result,Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
	    log.debug("<<예매 작성폼2>>");
	    
	    if((Long)bookingVO.getPerf_num() != null) {
	    	long perf_num = bookingVO.getPerf_num();
	    	loadBookingContents(perf_num, model);
	    }
	    
	    if (result.hasErrors()) {
	        model.addAttribute("errors", result.getAllErrors());
	        model.addAttribute("bookingVO", bookingVO);
	        return "bookingForm"; // 유효성 검증 실패 시 JSP로 이동
	    }
	    
	    bookingService.registerBookingInfo(bookingVO);
	    
	    if(bookingVO.getBooked_seat() == 1) {
	    	bookingService.updateSeatStatus(bookingVO.getSeat_num1());
	    }else {
	    	bookingService.updateSeatStatus(bookingVO.getSeat_num1());
	    	bookingService.updateSeatStatus(bookingVO.getSeat_num2());
	    }
	    
	    Long booking_num = bookingVO.getBooking_num();
	    log.debug("<<booking_num>> :" + booking_num);
	    
	    
	    model.addAttribute("message", "결제 페이지로 이동합니다");
		model.addAttribute("url",request.getContextPath() + "/charge/payment?pay_price=" + bookingVO.getTotal_price()+"&booking_num="+booking_num);
		    
		return "common/resultAlert";
	}
	
	@GetMapping("/register")
	public String registerForm(EventVO eventVO, @RequestParam long group_num, Model model, HttpServletRequest request) {
		
		if(eventVO == null) {
			eventVO = new EventVO();
		}
		
		AgroupVO group = artistService.selectArtistDetail(group_num);
		log.debug("Agroup: " + group);
		model.addAttribute("group", group);
		return "bookingRegister";
	}
	
	@PostMapping("/register")
	public String registerEvent(@ModelAttribute @Valid EventVO eventVO, BindingResult result, Model model, HttpServletRequest request) throws IllegalStateException, IOException {
			
		long group_num = eventVO.getArtist_num();
		AgroupVO group = artistService.selectArtistDetail(group_num);
		model.addAttribute("group", group);
		log.debug("Agroup2: " + group);
		if(result.hasErrors()) {
		    model.addAttribute("errors", result.getAllErrors());
		    model.addAttribute("eventVO", eventVO);
		    return "bookingRegister"; // 유효성 검증 실패 시 JSP로 이동
		}
		
		String uploadedPhoto = FileUtil.createFile(request, eventVO.getUpload());
		eventVO.setPerf_photo(uploadedPhoto);
		
		long hall_num = eventVO.getHall_num();
		String hall_name = null;
		if(hall_num == 1) {
			hall_name = "테스트 홀";
		}
		if(hall_num == 2) {
			hall_name = "서울 체육관";
		}
		if(hall_num == 3) {
			hall_name = "수도권 경기장";
		}
		if(hall_num == 4) {
			hall_name = "월드컵 돔";
		}
		
		eventVO.setHall_name(hall_name);
		if(eventVO.getEnd_date() == null) {
			eventVO.setEnd_date(eventVO.getPerf_date());
		}
		bookingService.registerEvent(eventVO);
		
		
		model.addAttribute("message", "공연 등록을 성공하였습니다");
		model.addAttribute("url",request.getContextPath() + "/booking/list?group_num="+group_num);
		    
		return "common/resultAlert";
	}
	
	@PostMapping("/complete")
    public ResponseEntity<String> completePayment(@RequestBody PaymentCompletionRequest request) {
		log.debug("<<complete 주소 이동 성공>>");
        try {
        	log.debug("<<complete 주소 이동 성공>> : try");
            // 결제 정보 객체 생성
            PaymentVO payment = new PaymentVO();
           
            payment.setTotalAmount(request.getTotalAmount());
            payment.setUSER_NUM(request.getUser_num());
            paymentService.updateBal(payment);
            // 결제 처리 서비스 호출
            paymentService.insertOrder(payment);
            
            bookingService.updateBookingPaymentStatus(request.getBooking_num());
            
           
            return ResponseEntity.ok("결제 완료 처리 성공");
        } catch (Exception e) {
        	  log.debug("<<complete 주소 이동 성공>> : catch");
        	  e.printStackTrace(); // 예외 출력
        	  bookingService.deleteBookingBeforePay(request.getBooking_num());
        	  return ResponseEntity.status(500).body("결제 처리 실패: " + e.getMessage());
        }
    }
	
	@GetMapping("/manage")
	public String bookingManagement(long group_num, @RequestParam(defaultValue="1") int pageNum,
									HttpServletRequest request, Model model,
									@AuthenticationPrincipal PrincipalDetails principal) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("user_num", group_num);
		int count = bookingService.selectEventRowCount(map);
		PagingUtil page = new PagingUtil(pageNum, count,20,10,"list");
		List<EventVO> list = null;
		if(count > 0) {

			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());


			list = bookingService.selectEventByArtistId(map);
		}
		AgroupVO group = artistService.selectArtistDetail(group_num);
		String group_name = group.getGroup_name();
		
		for(EventVO e : list) {
			if(e.getPerf_status().equals("before")) {
				e.setStatus_name("예매 전");
			}else if(e.getPerf_status().equals("ongoing")) {
				e.setStatus_name("예매 기간");
			}else if(e.getPerf_status().equals("membership")) {
				e.setStatus_name("선예매 기간");
			}else {
				e.setStatus_name("종료된 이벤트");
			}
			
		}
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());
		model.addAttribute("artist_num", group_num);
		model.addAttribute("group_name", group_name);
		
		return "bookingArtistList";
	}
	
	
	public void loadBookingContents(long perf_num, Model model) {
		EventVO event = bookingService.showEventDetail(perf_num);
    	AgroupVO group = artistService.selectArtistDetail(event.getArtist_num());
    	String group_name = group.getGroup_name();
    	
	    int seat_count = bookingService.countSeatByHallNum(event.getHall_num());
	    List<SeatVO> seats = bookingService.selectSeatByHallNum(event.getHall_num());
	    
	    
	    
	    for(SeatVO s : seats) {
	    	if(s.getSrow().equals("C")) {
	    		double d = event.getTicket_price() * 1.4;
	    		s.setPrice((int) Math.round(d));
	    	}else {
	    		s.setPrice(event.getTicket_price());
	    	}
	    	
	    	log.debug("<<S"+s.getSrow() +"가격: >>" + s.getPrice());
	    }
	    
	    
	    
	    model.addAttribute("group_name", group_name);
    	model.addAttribute("event", event);
	    model.addAttribute("seat_count", seat_count);
	    model.addAttribute("seats",seats);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete")
	public String deleteEvent(long perf_num, long group_num, Model model, HttpServletRequest request) {
		
		bookingService.deleteEvent(perf_num);
		
		model.addAttribute("message", "공연을 삭제하였습니다");
		model.addAttribute("url",request.getContextPath() + "/booking/manage?group_num="+group_num);
		    
		return "common/resultAlert";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/booking_list")
	public String userBooklist(long user_num, @RequestParam(defaultValue="1") int pageNum,
							   HttpServletRequest request, Model model,
							   @AuthenticationPrincipal PrincipalDetails principal) {
		
		if(user_num != principal.getMemberVO().getUser_num()) {
			model.addAttribute("message", "접근 권한이 없습니다");
			model.addAttribute("url",request.getContextPath() + "/main/main");
			return "common/resultAlert";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("user_num", user_num);
		int count = bookingService.countBookingByUserNum(user_num);
		List<BookingVO> list = null;
		PagingUtil page = new PagingUtil(pageNum, count,20,10,"list");
		
		if(count > 0) {

			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());

			
			list = bookingService.selectBookingByUserNum(map);
		}
		
		for(BookingVO b : list) {
			EventVO e = bookingService.showEventDetail(b.getPerf_num());
			String group_name = bookingService.getGroupNameByEvent(b.getPerf_num());
			b.setGroup_name(group_name);
			b.setPerf_title(e.getPerf_title());
			b.setPerf_photo(e.getPerf_photo());
		}
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());
		model.addAttribute("user_num", user_num);
		
		return "userBookingList";
	}
	
	
}
