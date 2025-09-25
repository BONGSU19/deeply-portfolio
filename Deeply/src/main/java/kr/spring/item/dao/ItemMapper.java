package kr.spring.item.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import kr.spring.item.vo.CartVO;
import kr.spring.item.vo.ItemVO;
import kr.spring.item.vo.OrderVO;
import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.MemberVO;

@Mapper
public interface ItemMapper {
	//------------------------------부모글-------------------------------------
	public List<ItemVO> selectList(Map<String,Object> map);
	public List<ItemVO> selecExceptPremium(Map<String,Object> map);
	@Select("SELECT * FROM shop_item WHERE user_num=#{user_num}")
	public List<ItemVO> selectListByUserNum(Long user_num);
	public int selectRowCount(Map<String,Object> map);
	public int insertItem(ItemVO Item);
	@Select("SELECT * FROM shop_item WHERE item_num=#{item_num}")
	public ItemVO selectitem(Long item_num);
	public void updateItem(ItemVO Item);
	@Delete("DELETE FROM shop_item WHERE item_num=#{item_num}")
	public void deleteItem(Long item_num);
	public List<ItemVO> showListByGroup();
	public List<ItemVO> showListGroup();
	//그룹 번호 가져오기
	@Select("SELECT * FROM agroup WHERE group_name=#{group_name}")
	public AgroupVO selectGroup(String group_name);
	
	//----------------------------구매자(사용자)----------------------------------
	public void insertOrder(OrderVO ordervo); //주문등록
	
	@Select("SELECT order_num_seq.nextval FROM dual")
	public Long selectBook_num();
	
	@Update("UPDATE shop_item SET item_stock=item_stock-#{quantity} WHERE item_num=#{item_num}")
	public void updateStock(int quantity, long item_num); // 재고 수 업데이트
	
	@Update("UPDATE shop_order SET pay_num=#{pay_num} WHERE order_num=#{order_num}")
	public void updatePayNum(long pay_num, long order_num);
	
	//---------------------------구매자(사용자) 주문목록-----------------------------------
	public List<OrderVO> getOrder(Long user_num);//주문내역 불러오기
	
	public int selectOrderRowCount(Map<String,Object> map);//전체 주문 개수/검색 주문 개수
	
	public OrderVO selectOrderDetail(OrderVO ordervo);//1건으로 불러올 개별 상품 목록
	
	public MemberVO selectUserInfo(MemberVO memberVO);//배송지 정보 가져오기
	
	@Delete("DELETE FROM shop_order WHERE item_num = #{item_num}")
	public void deleteOrder(Long item_num); //주문 취소
	
	
	
	//--------------------------------장바구니---------------------------------
	@Delete("DELETE FROM shop_cart WHERE item_num = #{item_num}")
	public void deleteCart(Long item_num);//장바구니에 담긴 상품 삭제
	
	public int insertCart(CartVO cart);//장바구니 정보 등록
	
	public List<CartVO> selectCart(long user_num);//장바구니 목록가져오기
	
	int updateCartByItem_num(@Param("item_num") long item_num, 
            			     @Param("user_num") long user_num, 
            			     @Param("order_quantity") long order_quantity); // 동일 상품 수량 합산 (최대 3개 제한) 
	
	@Select("SELECT * FROM shop_cart WHERE user_num=#{user_num} AND item_num=#{item_num}")
	public CartVO getCurrentQuantity(long user_num, long item_num);//유저, 아이템 넘버를 통한 현재 카트 주문량 구하기
	
	@Update("UPDATE shop_cart SET order_quantity=#{total_quantity} WHERE cart_num=#{cart_num}")
	public void updateTotalQuantity(int total_quantity, long cart_num);
	
	@Delete("DELETE FROM shop_cart WHERE cart_num=#{cart_num}")
	public void deleteCartByCartNum(long cart_num);//개별 카트 삭제
	
	@Select("SELECT * FROM shop_cart WHERE cart_num=#{cart_num}")
	public CartVO selectCartDetail(long cart_num);
	
	public int checkMembership(Map<String,Object> map);
	
	
	
	
	
	
	
	
	
	
	
	
	


}
