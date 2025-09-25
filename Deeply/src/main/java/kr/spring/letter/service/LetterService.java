package kr.spring.letter.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.scheduling.annotation.Scheduled;

import kr.spring.letter.vo.LetterVO;
import kr.spring.letter.vo.ReplyVO;
import kr.spring.payment.vo.FanVO;

public interface LetterService {
	public List<LetterVO> selectLetterByUser(Map<String,Object> map);
	public int countLetterByUser(Map<String,Object> map);
	public void postLetter(LetterVO letterVO);
	public LetterVO showLetterDetail(long letter_num);
	public void updateReplied(long letter_num);
	//reply
	public int countReply(Map<String,Object> map);
	public List<ReplyVO> showReplyForUser(Map<String,Object> map);
	//아티스트
	public int countLetterForArtist(Map<String,Object> map);
	public List<LetterVO> selectLetterForArtist(Map<String, Object> map);
	public int countReplyForArtist(long artist_num);
	public List<ReplyVO> showReplyForArtist(Map<String, Object> map);
	//아티스트 답장
	public void postReply(ReplyVO replyVO);
	public ReplyVO showReplyDetail(long reply_num);
	//삭제
	public void deleteLetter(long letter_num);
	public void deleteReply(long letter_num);
	//유료회원 정보
	public List<FanVO> getFanByArtistNum(long artist_num);
	public void minusLetterLimit(long user_num);
	public void resetLetterLimit();
	public int getLetterLimit(long user_num);
}
