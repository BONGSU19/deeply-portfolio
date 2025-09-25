package kr.spring.booking.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.booking.vo.BookingVO;
import kr.spring.event.vo.EventVO;
import kr.spring.seat.vo.SeatVO;


@Mapper
public interface BookingMapper {
	//아티스트 아이디로 이벤트 목록 정렬(페이징 처리)
	public List<EventVO> selectEventByArtistId(Map<String,Object> map);
	//이벤트 목록 개수(페이징 처리)
	public Integer selectEventRowCount(Map<String,Object> map);
	//공연 번호 이용해서 공연 상세 정보 불러오기
	@Select("SELECT * FROM performance_detail d LEFT OUTER JOIN performance_hall h ON d.hall_num = h.hall_num WHERE d.perf_num = #{perf_num}")
	public EventVO showEventDetail(long perf_num);
	//예매 정보 등록
	public void registerBookingInfo(BookingVO bookingVO);
	
	@Select("SELECT a.group_name FROM performance_detail p JOIN agroup a ON p.artist_num = a.group_num WHERE p.perf_num=#{perf_num}")
	public String getGroupNameByEvent(long perf_num);
	
	//공연장에 따른 좌석 개수
	@Select("SELECT COUNT(*) FROM seat WHERE hall_num=#{hall_num}")
	public int countSeatByHallNum(long hall_num);
	//공연장에 따른 좌석 정보
	@Select("SELECT * FROM seat WHERE hall_num=#{hall_num}")
	public List<SeatVO> selectSeatByHallNum(long hall_num);
	//공연 정보 등록
	public void registerEvent(EventVO eventVO);
	//공연 상태 자동 업데이트
	public void updatePerformanceStatus();
	//공연 결제 전 취소
	@Delete("DELETE FROM booking WHERE payment_status=0 AND booking_num=#{booking_num}")
	public void deleteBookingBeforePay(long booking_num);
	@Update("UPDATE booking SET payment_status=1 WHERE booking_num=#{booking_num}")
	public void updateBookingPaymentStatus(long booking_num);
	@Select("SELECT * FROM booking WHERE user_num=#{user_num} AND perf_num=#{perf_num}")
	public BookingVO getBookingNumBeforePay(long user_num, long perf_num);
	@Select("SELECT booking_seq.nextval FROM dual")
	public Long selectBook_num();
	
	@Delete("DELETE FROM performance_detail WHERE perf_num=#{perf_num}")
	public void deleteEvent(long perf_num);
	@Delete("DELETE FROM booking WHERE booking_num=#{perf_num}")
	public void deleteBooking(long perf_num);
	//아티스트 번호 fan 여부 비교 + 그룹 이름 체크
	public int checkIfGroupMembership(Map<String,Object> map);
	
	//회원 예매 정보 count
	public int countBookingByUserNum(long user_num);
	//회원 예매 정보 list
	public List<BookingVO> selectBookingByUserNum(Map<String,Object> map);
	//회원 예매 여부
	@Select("SELECT COUNT(*) FROM booking WHERE user_num=#{user_num} AND perf_num=#{perf_num}")
	public int countIfUserBooked(long user_num, long perf_num);
	
	
	//좌석 정보 업데이트
	@Update("UPDATE seat SET status='BOOKED' WHERE seat_num=#{seat_num}")
	public void updateSeatStatus(String seat_num);
	
	//좌석 리셋
	@Update("UPDATE seat SET status='AVAILABLE' WHERE hall_num=#{hall_num}")
	public void resetSeatStatus(long hall_num);
	
}
 