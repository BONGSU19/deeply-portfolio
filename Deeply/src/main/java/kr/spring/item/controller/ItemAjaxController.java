package kr.spring.item.controller;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.item.service.ItemService;
import kr.spring.item.vo.CartVO;
import kr.spring.item.vo.ItemVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/item")
public class ItemAjaxController {
	@Autowired
	private ItemService itemService;

	

	//자바빈(VO)초기화
	@ModelAttribute
	public ItemVO initCommand() {
		return new ItemVO();
	}

	/*==============================
	 * 	장바구니 추가
	 * =============================*/
	@PostMapping("/addCart")
	@ResponseBody
	public Map<String, Object> addCart(@RequestBody CartVO cartVO,
	                                   @AuthenticationPrincipal PrincipalDetails principal,
	                                   Model model, HttpServletRequest request
	                                   ) {

	    Map<String, Object> mapJson = new HashMap<>();

	    // 로그인 여부 확인
	    if (principal == null || principal.getMemberVO() == null) {
	        mapJson.put("result", "logout");
	        return mapJson;
	    }

	    // 사용자 정보 설정
	    cartVO.setUser_num(principal.getMemberVO().getUser_num());
	    
	    try {
	    	CartVO db_cart = itemService.getCurrentQuantity(cartVO.getUser_num(), cartVO.getItem_num());
	    	log.debug("<<db_cart>> : " + db_cart);
	    	int quantity = cartVO.getOrder_quantity();
	    	
	    	
	    	if(db_cart != null) {
	    		int db_quantity = db_cart.getOrder_quantity();
	    		int total_quantity = db_quantity + quantity;
	    		if(total_quantity > 3) {
		    		mapJson.put("result","over");
		    		return mapJson;
		    	}
	    		
	    		itemService.updateTotalQuantity(total_quantity, db_cart.getCart_num());
	    	}else {
	    		cartVO.setItem_quantity(quantity);
	    		itemService.insertCart(cartVO);
	    	}
	    	
	        
	        mapJson.put("result", "success");
	        
	    } catch (Exception e) {
	        mapJson.put("result", "error");
	    }
	    
	    // 사용자 장바구니 목록 가져오기
	    List<CartVO> cartList = itemService.selectCart(principal.getMemberVO().getUser_num());
	    log.debug("Cart List from DB: " + cartList);
	    log.debug("<<cart목록>> :" + cartList);

	    // Model에 데이터 추가
	    model.addAttribute("cart", cartList);
	    log.debug("<<cart목록>> :" + cartList);
	    
	    return mapJson;
	}
	
	
	/*
	 * 장바구니 수량 변경
	 * */
	@PostMapping("/modifyStock")
	@ResponseBody
	public Map<String, Object> modifyStock(@RequestBody CartVO cartVO,
	                                   @AuthenticationPrincipal PrincipalDetails principal,
	                                   Model model, HttpServletRequest request
	                                   ) {

	    Map<String, Object> mapJson = new HashMap<>();

	    // 로그인 여부 확인
	    if (principal == null || principal.getMemberVO() == null) {
	        mapJson.put("result", "logout");
	        return mapJson;
	    }

	    // 사용자 정보 설정
	    cartVO.setUser_num(principal.getMemberVO().getUser_num());
	    
	    try {
	    	log.debug("<<CartVO user_num : >>" + cartVO.getUser_num());
	    	log.debug("<<CartVO item_num : >>" + cartVO.getItem_num());
	    	log.debug("<<CartVO quantity : >>" + cartVO.getOrder_quantity());
	    	log.debug("<<CartVO cart_num : >>" + cartVO.getCart_num());
	        mapJson.put("result", "success");
	        itemService.updateTotalQuantity(cartVO.getOrder_quantity(), cartVO.getCart_num());
	    } catch (Exception e) {
	        mapJson.put("result", "error");
	    }
	    
	    // 사용자 장바구니 목록 가져오기
	    List<CartVO> cartList = itemService.selectCart(principal.getMemberVO().getUser_num());
	    log.debug("Cart List from DB: " + cartList);
	    log.debug("<<cart목록>> :" + cartList);

	    // Model에 데이터 추가
	    model.addAttribute("cart", cartList);
	    log.debug("<<cart목록>> :" + cartList);
	    
	    return mapJson;
	}
	


	


	
}
