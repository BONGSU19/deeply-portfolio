package kr.spring.letter.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.letter.dao.LetterMapper;
import kr.spring.letter.vo.LetterVO;
import kr.spring.letter.vo.ReplyVO;
import kr.spring.payment.vo.FanVO;

@Service
@Transactional
public class LetterServiceImpl implements LetterService{

	@Autowired
	LetterMapper letterMapper;
	
	@Override
	public List<LetterVO> selectLetterByUser(Map<String, Object> map) {
		return letterMapper.selectLetterByUser(map);
		
	}

	@Override
	public int countLetterByUser(Map<String, Object> map) {
		return letterMapper.countLetterByUser(map);
	}

	@Override
	public void postLetter(LetterVO letterVO) {
		letterMapper.postLetter(letterVO);
	}

	@Override
	public LetterVO showLetterDetail(long letter_num) {
		return letterMapper.showLetterDetail(letter_num);
	}
	
	@Override
	public void updateReplied(long letter_num) {
		letterMapper.updateReplied(letter_num);
		
	}
	//reply-------------------------------------------------------------------------------------
	@Override
	public int countReply(Map<String, Object> map) {
		return letterMapper.countReply(map);
	}

	@Override
	public List<ReplyVO> showReplyForUser(Map<String, Object> map) {
		return letterMapper.showReplyForUser(map);
	}
	//아티스트
	@Override
	public int countLetterForArtist(Map<String, Object> map) {
		return letterMapper.countLetterForArtist(map);
	}

	@Override
	public List<LetterVO> selectLetterForArtist(Map<String, Object> map) {
		return letterMapper.selectLetterForArtist(map);
	}

	@Override
	public int countReplyForArtist(long artist_num) {
		return letterMapper.countReplyForArtist(artist_num);
	}

	@Override
	public List<ReplyVO> showReplyForArtist(Map<String, Object> map) {
		return letterMapper.showReplyForArtist(map);
	}
	//아티스트 답장-------------------------------------------------------------------------------------
	@Override
	public void postReply(ReplyVO replyVO) {
		letterMapper.postReply(replyVO);
		updateReplied(replyVO.getLetter_num());
	}

	@Override
	public ReplyVO showReplyDetail(long reply_num) {
		return letterMapper.showReplyDetail(reply_num);
	}
	//삭제 ------------------------------------------------------------------------------------------
	@Override
	public void deleteLetter(long letter_num) {
		letterMapper.deleteLetter(letter_num);
		letterMapper.deleteReply(letter_num);
	}

	@Override
	public void deleteReply(long letter_num) {
		letterMapper.deleteReply(letter_num);
	}

	@Override
	public List<FanVO> getFanByArtistNum(long artist_num) {
		return letterMapper.getFanByArtistNum(artist_num);
	}

	@Override
	public void minusLetterLimit(long user_num) {
		letterMapper.minusLetterLimit(user_num);
		
	}
	
	@Scheduled(cron = "0 0 0 * * *")
	@Override
	public void resetLetterLimit() {
		letterMapper.resetLetterLimit();
		System.out.println("편지 개수 제한을 초기화하였습니다");
	}

	@Override
	public int getLetterLimit(long user_num) {
		return letterMapper.getLetterLimit(user_num);
		
	}

	

}
