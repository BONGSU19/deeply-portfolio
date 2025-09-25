package kr.spring.member.service;

import java.util.List;
import java.util.Map;

import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;



public interface ArtistService {
	public ArtistVO selectCheckMember(String id);
	public ArtistVO selectIdAndNickName(Map<String,String> map);
	public void insertMember(ArtistVO artist);
	
	//아티스트 페이지
	public List<AgroupVO> selectArtistByGroup();
	public AgroupVO selectArtistDetail(long group_num); 
	public List<ArtistVO> selectGroupMembers(long group_num);
	public List<ArtistVO> selectGroupMembersForFollower(Map<String,Long> map);
	public ArtistVO selectMember(long artist_num);
	
	//아티스트 수정
	public void updateArtistGroup(AgroupVO agroupVO);
	public void updateArtistMemberGroupName(Map<String,Object> map);
	
	//아티스트 번호로 agroupVO 검색
	public AgroupVO selectAgroupByArtistNum(long artist_num);
	
}
