package kr.spring.booking.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.booking.dao.BookingMapper;
import kr.spring.booking.vo.BookingVO;
import kr.spring.event.vo.EventVO;
import kr.spring.seat.vo.SeatVO;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	BookingMapper bookingMapper;

	@Override
	public List<EventVO> selectEventByArtistId(Map<String, Object> map) {
		return bookingMapper.selectEventByArtistId(map);
	}

	@Override
	public Integer selectEventRowCount(Map<String, Object> map) {
		return bookingMapper.selectEventRowCount(map);
	}

	@Override
	public EventVO showEventDetail(long perf_num) {
		return bookingMapper.showEventDetail(perf_num);
	}

	@Override
	public void registerBookingInfo(BookingVO bookingVO) {
		bookingVO.setBooking_num(bookingMapper.selectBook_num());
		bookingMapper.registerBookingInfo(bookingVO);
		
	}

	@Override
	public int countSeatByHallNum(long hall_num) {
		return bookingMapper.countSeatByHallNum(hall_num);
		
	}

	@Override
	public List<SeatVO> selectSeatByHallNum(long hall_num) {
		return bookingMapper.selectSeatByHallNum(hall_num);
	}

	@Override
	public void registerEvent(EventVO eventVO) {
		bookingMapper.registerEvent(eventVO);
		
	}
	
	@Scheduled(cron = "0 0 0 * * *")
	@Override
	public void updatePerformanceStatus() {
		System.out.println("상태 변경하였습니다");
		bookingMapper.updatePerformanceStatus();
	}

	@Override
	public void deleteBookingBeforePay(long booking_num) {
		bookingMapper.deleteBookingBeforePay(booking_num);
		
	}

	@Override
	public void updateBookingPaymentStatus(long booking_num) {
		bookingMapper.updateBookingPaymentStatus(booking_num);
		
	}

	@Override
	public BookingVO getBookingNumBeforePay(long user_num, long perf_num) {
		return bookingMapper.getBookingNumBeforePay(user_num, perf_num);
		
	}

	@Override
	public int checkIfGroupMembership(Map<String,Object> map) {
		return bookingMapper.checkIfGroupMembership(map);
	}

	@Override
	public void deleteEvent(long perf_num) {
		deleteBooking(perf_num);
		bookingMapper.deleteEvent(perf_num);
		
	}
	@Override
	public void deleteBooking(long perf_num) {
		bookingMapper.deleteBooking(perf_num);
	}

	@Override
	public int countBookingByUserNum(long user_num) {
		return bookingMapper.countBookingByUserNum(user_num);
	}

	@Override
	public List<BookingVO> selectBookingByUserNum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bookingMapper.selectBookingByUserNum(map);
	}

	@Override
	public String getGroupNameByEvent(long perf_num) {
		return bookingMapper.getGroupNameByEvent(perf_num);
	}

	@Override
	public void updateSeatStatus(String seat_num) {
		bookingMapper.updateSeatStatus(seat_num);
		
	}

	@Override
	public void resetSeatStatus(long hall_num) {
		bookingMapper.resetSeatStatus(hall_num);
		
	}

	@Override
	public int countIfUserBooked(long user_num, long perf_num) {
		return bookingMapper.countIfUserBooked(user_num, perf_num);
	}
	

	

	

}
