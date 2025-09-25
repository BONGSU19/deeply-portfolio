package kr.spring.follow.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.follow.vo.FollowVO;

@Mapper
public interface FollowMapper {
	//내가 팔로우하는 리스트
	List<FollowVO> getMyFollow(Map<String,Object> map);
	//내가 팔로우하는 사람 명수
	@Select("SELECT COUNT(*) FROM follow WHERE follower_num=#{follower_num}")
	public Integer countMyFollow(long follower_num);
	
	//팔로우
	@Select("SELECT * FROM follow WHERE follower_num=#{follower_num} AND follow_num=#{follow_num}")
	public FollowVO selectFollow(FollowVO follow);
	@Insert("INSERT INTO follow (follower_num, follow_num, follow_date) VALUES (#{follower_num},#{follow_num},sysdate)")
	public void following(FollowVO follow);
	@Delete("DELETE FROM follow WHERE follower_num=#{follower_num} AND follow_num=#{follow_num}")
	public void unfollow(FollowVO follow);
	
	//아티스트 입장에서 나를 팔로우하는 사람 명수
	@Select("SELECT COUNT(*) FROM follow WHERE follow_num=#{follow_num}")
	public Integer countMyFollower(long follow_num);
}
