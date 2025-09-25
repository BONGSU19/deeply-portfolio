package kr.spring.item.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.item.dao.ItemMapper;
import kr.spring.item.vo.CartVO;
import kr.spring.item.vo.ItemVO;
import kr.spring.item.vo.OrderVO;
import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.MemberVO;

@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemMapper itemMapper;

	@Override
	public List<ItemVO> selectList(Map<String, Object> map) {
		return itemMapper.selectList(map);
	}
	
	@Override
	public List<ItemVO> selectListByUserNum(Long user_num) {
		return itemMapper.selectListByUserNum(user_num);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return itemMapper.selectRowCount(map);
	}

	@Override
	public int insertItem(ItemVO Item) {
		return itemMapper.insertItem(Item);
	}

	@Override
	public ItemVO selectitem(Long item_num) {
		return itemMapper.selectitem(item_num);
	}

	@Override
	public void updateItem(ItemVO Item) {
		itemMapper.updateItem(Item);
	}

	@Override
	public void deleteItem(Long item_num) {
		itemMapper.deleteCart(item_num);
		itemMapper.deleteOrder(item_num);
		itemMapper.deleteItem(item_num);
	}

	@Override
	public List<ItemVO> showListByGroup() {
		return itemMapper.showListByGroup();
	}

	@Override
	public AgroupVO selectGroup(String group_name) {
		return itemMapper.selectGroup(group_name);
	}

	@Override
	public List<ItemVO> showListGroup() {
		return itemMapper.showListGroup();
	}

	@Override
	public void insertOrder(OrderVO ordervo) {
		ordervo.setOrder_num(itemMapper.selectBook_num());
		itemMapper.insertOrder(ordervo);
		
	}

	@Override
	public int selectOrderRowCount(Map<String, Object> map) {
		return 0;
	}

	@Override
	public OrderVO selectOrderDetail(OrderVO ordervo) {
		return null;
	}

	@Override
	public MemberVO selectUserInfo(MemberVO memberVO) {
		return null;
	}
	@Override
	public List<OrderVO> getOrder(Long user_num) {
		return itemMapper.getOrder(user_num);
	}

	@Override
	public void deleteOrder(Long item_num) {
		itemMapper.deleteOrder(item_num);
	}

	@Override
	public void deleteCart(Long item_num) {
		itemMapper.deleteCart(item_num);
	}

	@Override
	public int insertCart(CartVO cart) {
		return itemMapper.insertCart(cart);
	}

	@Override
	public List<CartVO> selectCart(long user_num) {
		return itemMapper.selectCart(user_num);
	}

	@Override
	public int updateCartByItem_num(long item_num, long user_num, long order_quantity) {
		return itemMapper.updateCartByItem_num(item_num, user_num, order_quantity);
	}

	@Override
	public CartVO getCurrentQuantity(long user_num, long item_num) {
		return itemMapper.getCurrentQuantity(user_num, item_num);
	}

	@Override
	public void updateTotalQuantity(int total_quantity, long cart_num) {
		itemMapper.updateTotalQuantity(total_quantity, cart_num);
		
	}

	@Override
	public void updateStock(int quantity, long item_num) {
		itemMapper.updateStock(quantity, item_num);
		
	}

	@Override
	public void updatePayNum(long pay_num, long order_num) {
		itemMapper.updatePayNum(pay_num, order_num);
		
	}

	@Override
	public void deleteCartByCartNum(long cart_num) {
		itemMapper.deleteCartByCartNum(cart_num);
		
	}

	@Override
	public CartVO selectCartDetail(long cart_num) {
		return itemMapper.selectCartDetail(cart_num);
		
	}

	@Override
	public int checkMembership(Map<String, Object> map) {
		return itemMapper.checkMembership(map);
	}

	@Override
	public List<ItemVO> selecExceptPremium(Map<String, Object> map) {
		return itemMapper.selecExceptPremium(map);
	}
	
	
	

	

	
	
	

	

	

	
}