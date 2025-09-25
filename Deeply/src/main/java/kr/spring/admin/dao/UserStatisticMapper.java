package kr.spring.admin.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.admin.vo.UserStatisticVO;

@Mapper
public interface UserStatisticMapper {

	  @Select("SELECT COUNT(*) FROM user_statistics WHERE record_date = #{date}")
	    boolean existsByDate(String date);

	    // 해당 날짜에 접속자 수 업데이트
	    @Update("UPDATE user_statistics SET active_users = active_users + 1 WHERE record_date = #{date}")
	    void updateUserCount(String date);

	    // 새로운 날짜에 접속자 수 추가
	    @Insert("INSERT INTO user_statistics (record_date, active_users) VALUES (#{date}, 1)")
	    void insertUserCount(String date);
	    @Select("SELECT TO_CHAR(record_date, 'yyyy-MM-dd') AS record_date, active_users FROM user_statistics ORDER BY record_date ASC")
	    List<UserStatisticVO> selectAllStatistics();
	    
}