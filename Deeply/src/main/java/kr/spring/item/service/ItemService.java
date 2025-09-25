package kr.spring.item.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import kr.spring.item.vo.CartVO;
import kr.spring.item.vo.ItemVO;
import kr.spring.item.vo.OrderVO;
import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.MemberVO;

public interface ItemService {
	//부모글
	public List<ItemVO> selectList(Map<String,Object> map);
	public List<ItemVO> selectListByUserNum(Long user_num);
	public int selectRowCount(Map<String,Object> map);
	public int insertItem(ItemVO Item);
	public ItemVO selectitem(Long item_num);
	public void updateItem(ItemVO Item);
	public void deleteItem(Long item_num);
	public List<ItemVO> showListByGroup();
	public List<ItemVO> showListGroup();
	public AgroupVO selectGroup(String group_name);
	
	public List<ItemVO> selecExceptPremium(Map<String,Object> map);
	
	//----------구매자(사용자)-------------
	public void insertOrder(OrderVO ordervo);
	public void updateStock(int quantity, long item_num);
	public void updatePayNum(long pay_num, long order_num);
	
	//----------구매자(사용자) 주문목록---------------
	public List<OrderVO> getOrder(Long user_num);
	public int selectOrderRowCount(Map<String,Object> map);
	public OrderVO selectOrderDetail(OrderVO ordervo);
	public MemberVO selectUserInfo(MemberVO memberVO);
	public void deleteOrder(Long item_num);
	
	//----------장바구니-------------
	public void deleteCart(Long item_num);
	public int insertCart(CartVO cart);
	public List<CartVO> selectCart(long user_num);
	public int updateCartByItem_num(long item_num, long user_num, long order_quantity);
	public CartVO getCurrentQuantity(long user_num, long item_num);
	public void updateTotalQuantity(int total_quantity, long cart_num);
	public void deleteCartByCartNum(long cart_num);
	public CartVO selectCartDetail(long cart_num);
	
	
	//--------유료회원----------------
	public int checkMembership(Map<String,Object> map);
	
}
