package kr.spring.video.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.video.vo.VideoCategoryVO;

@Mapper
public interface VideoCategoryMapper {

	List<VideoCategoryVO> selectCategoriesByGroupNum(Long groupNum);

    // 카테고리 추가
    @Insert("INSERT INTO video_categories (category_id, user_num, category_name) " +
            "VALUES (CATEGORY_SEQ.NEXTVAL, #{user_num}, #{category_name})")
    void insertCategory(VideoCategoryVO category);

 // user_num으로 group_num 가져오기
    @Select("SELECT ag.group_num FROM auser_detail ad JOIN agroup ag ON ad.group_name = ag.group_name"
    		+ " WHERE ad.user_num = #{userNum}")
    Long findGroupNumByUserNum(Long userNum);
}
