package kr.spring.notice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.notice.dao.NoticeMapper;
import kr.spring.notice.vo.NoticeVO;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService{
	@Autowired
	private NoticeMapper noticeMapper;

	@Override
	public List<NoticeVO> selectNotice(Map<String, Object> map) {
		return noticeMapper.selectNotice(map);
	}

	@Override
	public Integer countNotice(Map<String, Object> map) {
		return noticeMapper.countNotice(map);
	}

	@Override
	public List<NoticeVO> selectAllNotice(Map<String, Object> map) {
		return noticeMapper.selectAllNotice(map);
	}

	//@Override
	public NoticeVO getNoticeByNum(long notice_num) {
		return noticeMapper.getNoticeByNum(notice_num);
	}

	@Override
	public void insertNotice(NoticeVO notice) {
		noticeMapper.insertNotice(notice);
	}

	@Override
	public void deleteNotice(Long notice_num) {
		noticeMapper.deleteNotice(notice_num);
	}

	@Override
	public void deleteFile(Long notice_num) {
		noticeMapper.deleteFile(notice_num);
	}

	@Override
	public void updateNotice(NoticeVO notice) {
		noticeMapper.updateNotice(notice);
	}

	@Override
	public List<NoticeVO> selectMyNotice(Map<String, Object> map) {
		return noticeMapper.selectMyNotice(map);
	}

	@Override
	public Integer countMyNotice(Map<String, Object> map) {
		return noticeMapper.countMyNotice(map);
	}
}
