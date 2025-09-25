package kr.spring.commu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.commu.dao.CommuMapper;
import kr.spring.commu.vo.CommuReplyVO;
import kr.spring.commu.vo.CommuResponseVO;
import kr.spring.commu.vo.CommuVO;
import kr.spring.follow.vo.FollowVO;

@Service
@Transactional
public class CommuServiceImpl implements CommuService{
	@Autowired
	CommuMapper commuMapper;

	@Override
	public List<CommuVO> selectList(Map<String, Object> map) {
		return commuMapper.selectList(map);
	}

	@Override
	public Integer selectRowCount(Map<String, Object> map) {
		return commuMapper.selectRowCount(map);
	}

	@Override
	public void insertCommu(CommuVO commu) {
		commuMapper.insertCommu(commu);
	}
	@Override
	public void insertFanCommu(CommuVO commu) {
		commuMapper.insertFanCommu(commu);
	}

	@Override
	public CommuVO selectCommu(Long c_num) {
		return commuMapper.selectCommu(c_num);
	}

	@Override
	public void updateHit(Long c_num) {
		commuMapper.updateHit(c_num);
	}

	@Override
	public void updateCommu(CommuVO commu) {
		commuMapper.updateCommu(commu);
	}

	@Override
	public void deleteCommu(Long c_num) {
		//답글 삭제
		commuMapper.deleteResponseByCNum(c_num);
		//댓글이 존재하면 댓글을 우선 삭제하고 부모글을 삭제
		commuMapper.deleteReplyByCNum(c_num);
		//글 삭제
		commuMapper.deleteCommu(c_num);
	}

	@Override
	public void deleteFile(Long c_num) {
		commuMapper.deleteFile(c_num);
	}

	@Override
	public List<CommuReplyVO> selectListReply(Map<String, Object> map) {
		return commuMapper.selectListReply(map);
	}

	@Override
	public Integer selectRowCountReply(Map<String, Object> map) {
		return commuMapper.selectRowCountReply(map);
	}

	@Override
	public void insertReply(CommuReplyVO commuReply) {
		commuMapper.insertReply(commuReply);
	}

	@Override
	public CommuReplyVO selectReply(Long cre_num) {
		return commuMapper.selectReply(cre_num);
	}

	@Override
	public void updateReply(CommuReplyVO commuReply) {
		commuMapper.updateReply(commuReply);
	}

	@Override
	public void deleteReply(Long cre_num) {
		//답글 삭제
		commuMapper.deleteResponseByCreNum(cre_num);
		//댓글 삭제
		commuMapper.deleteReply(cre_num);
	}

	@Override
	public void deleteReplyByCNum(Long c_num) {
		commuMapper.deleteReplyByCNum(c_num);
	}

	@Override
	public List<CommuVO> selectMyList(Map<String,Object> map) {
		return commuMapper.selectMyList(map);
	}

	@Override
	public Integer selectMyRowCount(Map<String,Object> map) {
		return commuMapper.selectMyRowCount(map);
	}

	@Override
	public List<CommuVO> selectFreeList(Map<String, Object> map) {
		return commuMapper.selectFreeList(map);
	}

	@Override
	public Integer selectFreeRowCount(Map<String, Object> map) {
		return commuMapper.selectFreeRowCount(map);
	}

	@Override
	public List<CommuVO> selectFandomList(Map<String, Object> map) {
		return commuMapper.selectFandomList(map);
	}

	@Override
	public Integer selectFandomRowCount(Map<String, Object> map) {
		return commuMapper.selectFandomRowCount(map);
	}

	@Override
	public List<CommuResponseVO> selectListResponse(Long cre_num) {
		return commuMapper.selectListResponse(cre_num);
	}

	@Override
	public Integer selectResponseCount(Long cre_num) {
		return commuMapper.selectResponseCount(cre_num);
	}

	@Override
	public CommuResponseVO selectResponse(Long pe_num) {
		return commuMapper.selectResponse(pe_num);
	}

	@Override
	public void insertResponse(CommuResponseVO commuResponse) {
		commuMapper.insertResponse(commuResponse);
	}

	@Override
	public void updateResponse(CommuResponseVO commuResponse) {
		commuMapper.updateResponse(commuResponse);
	}

	@Override
	public Integer deleteResponse(Long pe_num) {
		return commuMapper.deleteResponse(pe_num);
	}

	@Override
	public void deleteResponseByCreNum(Long cre_num) {
		commuMapper.deleteResponseByCreNum(cre_num);
	}

	@Override
	public void deleteResponseByCNum(Long c_num) {
		commuMapper.deleteResponseByCNum(c_num);
	}

	@Override
	public void selectArtistName(FollowVO follow) {
		commuMapper.selectArtistName(follow);
	}
}
