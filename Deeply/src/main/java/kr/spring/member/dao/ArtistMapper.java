package kr.spring.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.ArtistVO;




@Mapper
public interface ArtistMapper {
	 @Select("SELECT SEQ_USER_NUM.nextval FROM dual")
	    long selectUser_num();
	 @Select("SELECT AGROUP_SEQ.nextval FROM dual")
	 long selecAgroup_num();
	    // 사용자 정보를 DB에 삽입하는 메소드
	    @Insert("INSERT INTO auser(user_num, id) VALUES (#{user_num, jdbcType=BIGINT}, #{id})")
	    void insertMember(ArtistVO artist);
	public void insertMember_detail(ArtistVO artist);
	public ArtistVO selectCheckMember(String id);
	public ArtistVO selectIdAndNickName(Map<String,String> map);
	@Select("SELECT * FROM agroup WHERE group_name = #{groupName}")
	public AgroupVO findByGroupName(String group_name);
	@Insert("insert into agroup(group_num,group_name) values (#{group_num},#{group_name})")
	public void insertAgroup(AgroupVO group);
	//아티스트 페이지
	@Select("SELECT * FROM agroup")
	public List<AgroupVO> selectArtistByGroup();
	
	@Select("SELECT * FROM agroup WHERE group_num=#{group_num}")
	public AgroupVO selectArtistDetail(long group_num); 
	

	public List<ArtistVO> selectGroupMembers(long group_num);
	
	public List<ArtistVO> selectGroupMembersForFollower(Map<String,Long> map);

	
	@Select("SELECT * FROM auser JOIN auser_detail USING (user_num) WHERE user_num=#{user_num}")
	public ArtistVO selectMember(long artist_num);
	
	@Select("SELECT * FROM agroup g JOIN auser_detail d USING(group_name) WHERE d.user_num=#{artist_num}")
	public AgroupVO selectAgroupByArtistNum(long artist_num);
	
	//프로필 이미지 업데이트
	@Update("UPDATE auser_detail SET photo=#{photo},photo_name=#{photo_name} WHERE user_num=#{user_num}")
	public void updateProfile(ArtistVO member);
	
	@Update("UPDATE agroup SET group_name=#{group_name},fandom_name=#{fandom_name},intro_desc=#{intro_desc},group_photo=#{group_photo} WHERE group_num=#{group_num}")
	public void updateArtistGroup(AgroupVO agroupVO);
	@Update("UPDATE auser_detail SET group_name = #{agroupVO.group_name} WHERE group_name = #{origin_name}")
	public void updateArtistMemberGroupName(Map<String,Object> map);
	
}
