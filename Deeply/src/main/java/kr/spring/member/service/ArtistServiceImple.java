package kr.spring.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.member.dao.ArtistMapper;
import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.ArtistVO;

@Service
@Transactional
public class ArtistServiceImple implements kr.spring.member.service.ArtistService {
	@Autowired
	private ArtistMapper artistMapper;
	@Override
	public ArtistVO selectCheckMember(String id) {
		
		return artistMapper.selectCheckMember(id);
	}
	@Override
	public ArtistVO selectIdAndNickName(Map<String, String> map) {
		return artistMapper.selectIdAndNickName(map);
	}
	@Override
	public void insertMember(ArtistVO artist) {
		
		   if(artistMapper.findByGroupName(artist.getGroup_name())==null) {
			   AgroupVO agroup = new AgroupVO();  // 객체 생성
	    		long group_num =artistMapper.selecAgroup_num();
	    		
	    		agroup.setGroup_num(group_num);
	    		agroup.setGroup_name(artist.getGroup_name());
	        	artistMapper.insertAgroup(agroup);
	        }
		   long user_num = artistMapper.selectUser_num(); 
			artist.setUser_num(user_num);
			  if (artist.getIntro() == null || artist.getIntro().isEmpty()) {
				  artist.setIntro((artist.getName() + "의 자기소개입니다."));
		        }
			
        // 회원 정보를 삽입
        artistMapper.insertMember(artist);
        artistMapper.insertMember_detail(artist);
     
		
	}
	
	//아티스트 페이지
	@Override
	public List<AgroupVO> selectArtistByGroup() {
		return artistMapper.selectArtistByGroup();
	}

	@Override
	public AgroupVO selectArtistDetail(long group_num) {
		return artistMapper.selectArtistDetail(group_num);
	}
	
	@Override
	public List<ArtistVO> selectGroupMembers(long group_num) {
		return artistMapper.selectGroupMembers(group_num);
	}
	@Override
	public ArtistVO selectMember(long artist_num) {
		return artistMapper.selectMember(artist_num);
	}
	
	//아티스트 수정
	@Override
	public void updateArtistGroup(AgroupVO agroupVO) {
		artistMapper.updateArtistGroup(agroupVO);
		
	}
	@Override
	public void updateArtistMemberGroupName(Map<String, Object> map) {
		artistMapper.updateArtistMemberGroupName(map);
	}
	@Override
	public AgroupVO selectAgroupByArtistNum(long artist_num) {
		return artistMapper.selectAgroupByArtistNum(artist_num);
	}
	@Override
	public List<ArtistVO> selectGroupMembersForFollower(Map<String, Long> map) {
		return artistMapper.selectGroupMembersForFollower(map);
	}

	
	




}
