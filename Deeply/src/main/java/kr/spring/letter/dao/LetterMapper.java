package kr.spring.letter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.letter.vo.LetterVO;
import kr.spring.letter.vo.ReplyVO;
import kr.spring.payment.vo.FanVO;

@Mapper
public interface LetterMapper {
	
	//편지vo
	public List<LetterVO> selectLetterByUser(Map<String,Object> map);
	public int countLetterByUser(Map<String,Object> map);
	public void postLetter(LetterVO letterVO);
	@Select("SELECT * FROM letter WHERE letter_num=#{letter_num}")
	public LetterVO showLetterDetail(long letter_num);
	@Update("UPDATE letter SET replied=1 WHERE letter_num=#{letter_num}")
	public void updateReplied(long letter_num);
	
	//Reply
	public int countReply(Map<String,Object> map);
	public List<ReplyVO> showReplyForUser(Map<String,Object> map);
	
	//아티스트
	public int countLetterForArtist(Map<String,Object> map);
	public List<LetterVO> selectLetterForArtist(Map<String, Object> map);
	public int countReplyForArtist(long artist_num);
	public List<ReplyVO> showReplyForArtist(Map<String, Object> map);
	
	//아티스트 답장
	public void postReply(ReplyVO replyVO);
	@Select("SELECT * FROM reply JOIN duser USING(user_num) WHERE reply_num=#{reply_num}")
	public ReplyVO showReplyDetail(long reply_num);
	
	//삭제
	@Delete("DELETE FROM letter WHERE letter_num=#{letter_num}")
	public void deleteLetter(long letter_num);
	
	@Delete("DELETE FROM reply WHERE letter_num=#{letter_num}")
	public void deleteReply(long letter_num);
	
	//유료회원 정보
	@Select("SELECT * FROM fan WHERE fan_artist=#{artist_num}")
	public List<FanVO> getFanByArtistNum(long artist_num);
	
	@Update("UPDATE duser_detail SET letter_limit=letter_limit-1 WHERE user_num=#{user_num}")
	public void minusLetterLimit(long user_num);
	
	@Update("UPDATE duser_detail SET letter_limit=3 WHERE letter_limit != 3")
	public void resetLetterLimit();
	
	@Select("SELECT letter_limit FROM duser_detail WHERE user_num=#{user_num}")
	public int getLetterLimit(long user_num);
}
