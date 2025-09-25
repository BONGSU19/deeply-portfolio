package kr.spring.notice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.commu.vo.CommuVO;
import kr.spring.notice.vo.NoticeVO;

@Mapper
public interface NoticeMapper{
	//공지 목록 보기
	public List<NoticeVO> selectNotice(Map<String,Object> map);
	public Integer countNotice(Map<String,Object> map);
	//팔로우하는 사람이 없을 때
	public List<NoticeVO> selectAllNotice(Map<String,Object> map);
	//연예인 입장에서 내 공지 목록
	public List<NoticeVO> selectMyNotice(Map<String,Object> map);
	public Integer countMyNotice(Map<String,Object> map);
	
	//공지 상세 보기
	@Select("SELECT * FROM Notice JOIN auser_detail USING(user_num) WHERE notice_num=#{notice_num}")
	public NoticeVO getNoticeByNum(long notice_num);
	
	//공지 작성하기
	public void insertNotice(NoticeVO notice);
	//글 수정
	public void updateNotice(NoticeVO notice);
	//공지 삭제하기
	@Delete("DELETE FROM notice WHERE notice_num=#{notice_num}")
	public void deleteNotice(Long notice_num);
	@Update("UPDATE notice SET notice_file='' WHERE notice_num=#{notice_num}")
	public void deleteFile(Long notice_num);
}
