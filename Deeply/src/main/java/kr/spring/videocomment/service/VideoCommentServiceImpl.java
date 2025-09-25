package kr.spring.videocomment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.videocomment.dao.VideoCommentMapper;
import kr.spring.videocomment.vo.VideoCommentVO;

@Service
public class VideoCommentServiceImpl implements VideoCommentService {

    @Autowired
    private VideoCommentMapper videoCommentMapper;

    @Override
    public void insertComment(VideoCommentVO videoCommentVO) {
        // 초기 값 설정
        if (videoCommentVO.getParentCommentId() == null) {
            videoCommentVO.setParentCommentId(0L); // 또는 NULL을 허용하도록 DB 설정
        }

        videoCommentMapper.insertComment(videoCommentVO);
    }
    
 // 2) 해당 영상 댓글 목록 조회
    @Override
    public List<VideoCommentVO> selectCommentsByVideoId(Long videoId) {
        return videoCommentMapper.selectCommentsByVideoId(videoId);
    }
}
