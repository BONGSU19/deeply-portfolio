package kr.spring.item.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.item.service.ItemService;
import kr.spring.item.vo.CartVO;
import kr.spring.item.vo.ItemVO;
import kr.spring.item.vo.OrderVO;
import kr.spring.member.service.ArtistService;
import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.payment.service.PaymentService;
import kr.spring.payment.vo.PaymentCompletionRequest;
import kr.spring.payment.vo.PaymentVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import kr.spring.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private PaymentService paymentService;

	//자바빈(VO)초기화
	@ModelAttribute
	public ItemVO initCommand() {
		return new ItemVO();
	}
 
	/*==============================
	 *  상품 등록
	 * =============================*/
	// 등록 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/write")
	public String form() {
	    return "itemWrite"; // Tiles View 이름 반환
	}

	// 전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/write")
	public String submit(@Valid ItemVO itemVO, 
						BindingResult result, 
						HttpServletRequest request,
						RedirectAttributes redirect,
						@AuthenticationPrincipal PrincipalDetails principal) throws IllegalStateException, IOException {

	    log.debug("<<상품 등록>> :" + itemVO);

	    //파일 유효성 체크
	    if(itemVO.getUpload() == null || itemVO.getUpload().isEmpty()) {
	    	result.rejectValue("upload", "file.required");
	    }
	    
	    // 유효성 체크 결과 오류가 있으면 폼 호출
	    if (result.hasErrors()) {
	    	ValidationUtil.printErrorFields(result);
	    	log.debug("<<FORM 리다이렉트>>");
	        return form();
	    }

	    // 회원 번호 읽기
	    ArtistVO vo = principal.getArtistVO();
	    String group_name = vo.getGroup_name();
	    AgroupVO agroupVO = itemService.selectGroup(group_name);
	    itemVO.setUser_num(agroupVO.getGroup_num());
	    // 파일 업로드
	    itemVO.setFilename(FileUtil.createFile(request, itemVO.getUpload()));
	    
	    
	    // 상품 등록하기
	    itemService.insertItem(itemVO);
	    	
	    redirect.addFlashAttribute("result", "success");
	    log.debug("성공");
	    return "redirect:/item/main";
	}

	
	/*==============================
	 * 	상품 메인
	 * =============================*/
	
	 @GetMapping("/main")
	 public String getMain(String keyfield,
			 			   String keyword,
			 			   Model model,
			 			   @AuthenticationPrincipal PrincipalDetails principal) {
		log.debug("<<PrincipalDetails 객체>>: " + principal);

		Map<String,Object> map = new HashMap<String,Object>();
		
		if(principal != null) {
			//아티스트 계정으로 접속
			if(principal.getArtistVO() != null) {
				ArtistVO artist = principal.getArtistVO();
				Long auser_num = artist.getUser_num();
				map.put("user_num", auser_num);
				model.addAttribute("auser_num", auser_num);
			}
			
			//유저 계정으로 접속
			else if(principal.getMemberVO() != null) {
				MemberVO member = principal.getMemberVO();
				Long duser_num = member.getUser_num();
				map.put("user_num", duser_num);
				model.addAttribute("member", member);
			}
		}
		
		List<ItemVO> list = itemService.showListByGroup();
		List<ItemVO> group = itemService.showListGroup();

		model.addAttribute("list", list);
		model.addAttribute("group", group);

		return "itemMain";
	}
	 
	
	/*==============================
	 * 	상품 목록
	 * =============================*/
	@GetMapping("/list")
	public String getList(@RequestParam(defaultValue="1") int pageNum,
						  Long user_num,//그룹 넙
						  String keyfield,
						  String keyword,
						  Model model,
						  @AuthenticationPrincipal PrincipalDetails principal) {

		log.debug("<<user_num>> : " + user_num);
		
		Long my_num = null;
		int isMember = 0;
		
		if(principal != null && principal.getMemberVO() != null) {
			my_num = principal.getMemberVO().getUser_num();
			Map<String,Object> mapMembership = new HashMap<String,Object>();
			AgroupVO agroup = artistService.selectArtistDetail(user_num);
			String group_name = agroup.getGroup_name();
			mapMembership.put("user_num", my_num);
			mapMembership.put("group_name", group_name);
			isMember = itemService.checkMembership(mapMembership);
		}
		
		
		
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(principal != null) {
		//아티스트 계정으로 접속
		if(principal.getArtistVO() != null) {
			ArtistVO artist = principal.getArtistVO();
			Long auser_num = artist.getUser_num();
			map.put("user_num", auser_num);
			model.addAttribute("auser_num", auser_num);
			isMember = 1;
		}
		
		//유저 계정으로 접속
		else if(principal.getMemberVO() != null) {
			MemberVO member = principal.getMemberVO();
			Long duser_num = member.getUser_num();
			map.put("user_num", duser_num);
			model.addAttribute("member", member);
		  }
		}
		
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		map.put("user_num", user_num);

		//전체/검색 레코드수 
		int count = itemService.selectRowCount(map);

		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum,count,12,10,"list", "&user_num="+user_num);
		
		
		List<ItemVO> list = null;
		
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			
			if(isMember > 0) {
				list = itemService.selectList(map);
			}else {
				list = itemService.selecExceptPremium(map);
			}
			
		}
		
		
		//그룹 넘버를 사용해서 그룹에 포함된 모든 아티스트vo List 형태로 받아오기(List<ArtistVO> artistVO = 메서드)
		//principal 값으로 유저넘버 받아오기 long us_num
		//FAN VO에서 아티스트 넘버와 유저넘버를 비교하여 일치하는 값의 개수 -> XML COUNT(*) 형식으로 받기
		//COUNT 값이 1 이상이면 구독한 상태 / 아니면 구독 안한상태
		// 이거로 현재 로그인한 유저가 구독했는지 파악하고 ITEMVO에 PRIVATE INT is_member 이런식으로 값 하나 만들어서ㅏ
		// set_isMember() 이렇게 vo 값에다가 임시로 현재 구독 상태 0 or 1로 저장
		// 그 값을 model에 담아서 jsp에서 if문으로 활용
		
		
		model.addAttribute("user_num", user_num);
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());
		return "itemList";
	}
	
	/*==============================
	 * 	상품 상세
	 * =============================*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/detail")
	public String process(long item_num,
						  Model model,
						  @AuthenticationPrincipal 
						  PrincipalDetails principal){
		
		log.debug("<<PrincipalDetails 객체>>: " + principal);
		log.debug("<<상품 상세 - item_num>> : " + item_num);
		
		
		
		
		ItemVO item = itemService.selectitem(item_num);
		AgroupVO agroup = artistService.selectArtistDetail(item.getUser_num());
		
		if(principal.getMemberVO() != null) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("user_num", principal.getMemberVO().getUser_num());
			map.put("group_name", agroup.getGroup_name());
			int isMember = itemService.checkMembership(map);
			model.addAttribute("isMember", isMember);
		}else {
			model.addAttribute("isMember", 0);
		}
		
		model.addAttribute("item",item);
		model.addAttribute("orderVO",new OrderVO());
		model.addAttribute("agroup", agroup);
		
		return "itemView";
	}

	

	
	/*==============================
	 * 	상품 글 수정
	 * =============================*/
	//수정폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/update")
	public String formUpdate(@RequestParam("item_num") long item_num,
							 Model model,
						     @AuthenticationPrincipal 
							 PrincipalDetails principal) {
		log.debug("<<getArtistVO 객체>>: " + principal.getArtistVO());
		
		long artist_num = principal.getArtistVO().getUser_num();
		AgroupVO agroupVO = artistService.selectAgroupByArtistNum(artist_num);
		
		ItemVO itemVO = itemService.selectitem(item_num);
		log.debug("AgroupVO 정보 : " + agroupVO);
		log.debug("<<등록된 상품 정보>> : " +itemVO);
		log.debug("<<list에서 받아온 item_num>> : " +item_num);
		log.debug("<<상품 작성자 유저 번호>> : " + itemVO.getUser_num());
		log.debug("<<등록할 떄 ItemVO filename>>: " + itemVO.getFilename());
		
		
		//DB에 저장된 파일 정보 구하기
		if(agroupVO.getGroup_num() != itemVO.getUser_num()) {
			return "redirect:common/accessDenied";
		}else {		
		model.addAttribute("item",itemVO);
		log.debug("<<등록된 상품 정보>> : " +itemVO);
		log.debug("<<등록 완료한 ItemVO filename>>: " + itemVO.getFilename());
		return "itemModify";
		}	
	}
	
	
	//수정폼에서 전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update")
	public String submitUpdate(@Valid ItemVO itemVO,
					            BindingResult result,
					            @RequestParam("item_num") long item_num,
					            Long user_num,
					            HttpServletRequest request,
					            Model model,
					            @AuthenticationPrincipal
					            PrincipalDetails principal)
					            		throws IllegalStateException, IOException{
		
		if(principal != null) {
			//아티스트 계정으로 접속
			if(principal.getArtistVO() != null) {
				ArtistVO artist = principal.getArtistVO();
				Long auser_num = artist.getUser_num();
			}
			
			
		}
		log.debug("<<글 수정 item_num>> : " + item_num);
		log.debug("<<글 수정>> : " + itemVO);
		
		 //DB에 저장된 파일 정보 구하기
        ItemVO db_item = itemService.selectitem(itemVO.getItem_num());
        log.debug("<<db_item 정보>> : " + db_item);
        
        //파일 유효성 체크
       if(itemVO.getUpload() == null || itemVO.getUpload().isEmpty()) {
    	   if(db_item.getFilename() != null) {
    		   itemVO.setFilename(db_item.getFilename());
    	   }
       }else {
    	   itemVO.setFilename(FileUtil.createFile(request, itemVO.getUpload()));
       }
	    
	   
	    
	    // 유효성 체크 결과 오류가 있으면 폼 호출
	    if (result.hasErrors()) {
	    	ValidationUtil.printErrorFields(result);
	  	log.debug("<<FORM 리다이렉트>>");
	        //return form();
	  		model.addAttribute("item", db_item); // 기존 데이터 유지
	  		return "itemModify"; // 수정 폼 반환
	    }
	    
        
        


        //파일을 교체했을 경우 기존 파일을 삭제
       // if(itemVO.getFilename() != null && !itemVO.getUpload().isEmpty()) {
            //기존 파일(수정 작업 전 파일) 삭제 처리
            //FileUtil.removeFile(request, db_item.getFilename());
        //}
        
        //view에 표시할 메세지
        model.addAttribute("message","글 수정 완료!!");
        model.addAttribute("url",request.getContextPath() + "/item/detail?item_num=" + itemVO.getItem_num());

        //글 수정
        itemService.updateItem(itemVO);

        return "common/resultAlert";
    }
	
	
	/*==============================
	 * 	상품 글 삭제
	 * =============================*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete")
	public String submitDelete(@RequestParam("item_num") long item_num,
								Model model,
								HttpServletRequest request,
								@AuthenticationPrincipal 
								PrincipalDetails principal) 
										throws IllegalStateException, IOException{
		log.debug("<<삭제할 상품 번호>> : " + item_num);
		
		//DB에 저장된 파일 정보 구하기
		ItemVO db_item = itemService.selectitem(item_num);
		
		long artist_num = principal.getArtistVO().getUser_num();
		AgroupVO agroupVO = artistService.selectAgroupByArtistNum(artist_num);
		
		//로그인 일치시에 삭제하기->
		if(agroupVO.getGroup_num() != db_item.getUser_num()) {
		//if(principal.getArtistVO().getUser_num() != db_item.getUser_num()) {
			
			log.debug("<<getAgroupVO>> : " + agroupVO.getGroup_num());
			log.debug("<<getItemVO user_num>> : " + db_item.getUser_num());
			return "redirect:/common/accessDenied";
		}
		
		//글 삭제
		itemService.deleteItem(item_num);
		
		//파일 삭제
		if(db_item.getFilename() != null) {
			FileUtil.removeFile(request, db_item.getFilename());
		}
		
		model.addAttribute("message","글 삭제 완료");
	    model.addAttribute("url",request.getContextPath() + "/item/main");
		return "common/resultAlert";
	}
	



	/*==============================
	 * 			주문 등록
	 * =============================*/
	//주문 폼(ItemView.jsp)
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/order")
	public String order(long item_num, int quantity, MemberVO memberVO,
						Model model,HttpServletRequest request,@AuthenticationPrincipal 
						PrincipalDetails principal) throws IllegalStateException, IOException{
		ItemVO item = itemService.selectitem(item_num);
		
		//유저 계정으로 로그인 하지 않았을 시에 로그인 창을 호출함
		if(principal.getMemberVO() == null) {
			model.addAttribute("message", "사용자 로그인 후 구매가 가능합니다.");
			model.addAttribute("url",request.getContextPath() + "/item/main");
			return "common/resultAlert";
		}
		
		if(item.getItem_stock() < quantity) {
			model.addAttribute("message", "재고가 부족합니다, 현재 상품은 " + item.getItem_stock() +"개 남았습니다");
			model.addAttribute("url",request.getContextPath() + "/item/detail?item_num="+item_num);
			return "common/resultAlert";
		}
		
		OrderVO orderVO = new OrderVO();
		
		model.addAttribute("orderVO", orderVO);
		model.addAttribute("item", item);
		model.addAttribute("quantity", quantity);
		
		
		return "itemOrder";
	}
	//주문 폼에서 주문 데이터 전송
	
	@PostMapping("/order")
	public String submitOrder(@ModelAttribute("orderVO") @Valid OrderVO orderVO, 
							BindingResult result,
			 				HttpServletRequest request,
			 				Model model){
		
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			model.addAttribute("orderVO", orderVO);
			log.debug("<<errors: >> : " + result.getAllErrors());
			log.debug("<<OrderVO >> : " + orderVO);
			return "itemOrder"; // 유효성 검증 실패 시 JSP로 이동
		}
		
		itemService.insertOrder(orderVO);
		long order_num = orderVO.getOrder_num();
		
		model.addAttribute("message", "결제 페이지로 이동합니다");
		model.addAttribute("url",request.getContextPath() + "/charge/payment?pay_price=" + 
							orderVO.getTotal_price()+"&order_num="+order_num+"&item_quantity="+orderVO.getItem_quantity());
		    
		return "common/resultAlert";
	}
	

	
	/*==============================
	 * 			장바구니 
	 * =============================*/
	
	//장바구니 목록
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/cart")
	public String cart(
		long user_num,
	    Model model,
	    HttpServletRequest request,
	    @AuthenticationPrincipal PrincipalDetails principal) throws IllegalStateException, IOException {

		//유저 계정으로 로그인 하지 않았을 시에 로그인 창을 호출함
		if(principal.getMemberVO() == null) {
			model.addAttribute("message", "사용자 로그인 후 구매가 가능합니다.");
			model.addAttribute("url",request.getContextPath() + "/item/main");
			return "common/resultAlert";
		}
		
		List<CartVO> carts = itemService.selectCart(user_num);
		
		//장바구니내 총 합
		int totalAmount = 0;
		for (CartVO cartItem : carts) {
		    totalAmount += cartItem.getItem_price() * cartItem.getOrder_quantity();
		}
		
		
		
		
		if(principal.getMemberVO() != null) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("user_num", user_num);
			
			for(CartVO cart : carts) {
				ItemVO item = itemService.selectitem(cart.getItem_num());
				cart.setIsPremium(item.getCategory());
				AgroupVO agroup = artistService.selectArtistDetail(item.getUser_num());
				String group_name = agroup.getGroup_name();
				map.put("group_name", group_name);
				int isMember = itemService.checkMembership(map);
				if(isMember > 0) {
					cart.setIsMember(1);
				}
			}
			
		}
		
		
		log.debug("<<장바구니 목록>> : " + carts);
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("cart", carts);
	    
	    return "itemCart"; // 장바구니 페이지로 이동
	
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
            
            //재고 quantity만큼 감소
            itemService.updateStock(request.getItem_quantity(),request.getOrder_num());
            //paynum 넣기
            itemService.updatePayNum(payment.getPAY_NUM(), request.getOrder_num());
           
            return ResponseEntity.ok("결제 완료 처리 성공");
        } catch (Exception e) {
        	  log.debug("<<complete 주소 이동 성공>> : catch");
        	  e.printStackTrace(); // 예외 출력
        	  //결제 정보 삭제
        	  return ResponseEntity.status(500).body("결제 처리 실패: " + e.getMessage());
        }
    }
	
	
	/*==============================
	 * 	장바구니 삭제
	 * =============================*/
	@GetMapping("/delete_cart")
	public String deleteCart(long cart_num, Model model,
						   HttpServletRequest request,
						   @AuthenticationPrincipal PrincipalDetails principal) {
		CartVO cartVO = itemService.selectCartDetail(cart_num);
		
		if(principal.getMemberVO() != null && principal.getMemberVO().getUser_num() == cartVO.getUser_num()) {
			itemService.deleteCartByCartNum(cart_num);
			
			model.addAttribute("message", "장바구니 상품을 삭제하였습니다");
			model.addAttribute("url",request.getContextPath() + "/item/cart?user_num="+principal.getMemberVO().getUser_num());
			    
			return "common/resultAlert";
		}
		//카트 vo에 저장된 유저 번호와 로그인된 유저번호가 일치하지 않을 경우 삭제 방지
		model.addAttribute("message", "삭제 권한이 없습니다");
		model.addAttribute("url",request.getContextPath() + "/item/cart?user_num="+principal.getMemberVO().getUser_num());
		    
		return "common/resultAlert";
	}
	
	/*=============================
	 *  장바구니 전체 구매
	 *============================= */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/orders")
	public String orderItems(int price, Model model,
							 HttpServletRequest request,
							 @AuthenticationPrincipal PrincipalDetails principal) {
		
		
		if(principal.getMemberVO() != null) {
			long user_num = principal.getMemberVO().getUser_num();
			List<CartVO> list = itemService.selectCart(user_num);
			
			for(CartVO c : list) {
				ItemVO item = itemService.selectitem(c.getItem_num());
				c.setFilename(item.getFilename());
				c.setItem_name(item.getItem_name());
			}
			
			model.addAttribute("list", list);
		}
		
		
		
		return "itemTotalOrder";
	}
	
	/*==============================
	 * 	마이페이지 주문내역
	 * =============================*/
	@GetMapping("/orderList")
	public String itemOrderList(Model model,
								HttpServletRequest request,
								@AuthenticationPrincipal PrincipalDetails principal) {
		
		
		
		if (principal != null && principal.getMemberVO() != null) {
	        long user_num = principal.getMemberVO().getUser_num();

	        // 주문 목록 가져오기
	        List<OrderVO> orderList = itemService.getOrder(user_num);
	        
	        log.debug("<<주문내역 리스트>> : " + orderList);
		 
			//장바구니내 총 합 (장바구니 모든 상품 구매하기 후에 고치기)
			int priceByItems = 0;
				for (OrderVO order : orderList) {
					priceByItems += order.getTotal_price()-(order.getItem_price()*order.getItem_quantity());
				}
			
				
			
			
			// 주문 목록 모델에 추가
		    model.addAttribute("orderList", orderList);
			model.addAttribute("priceByItems", priceByItems);}
		 
			return "itemOrderList"; // 주문내역으로 이동
	}

	



}



















