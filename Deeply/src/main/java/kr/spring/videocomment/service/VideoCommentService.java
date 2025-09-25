package kr.spring.videocomment.service;

import java.util.List;

import kr.spring.videocomment.vo.VideoCommentVO;

public interface VideoCommentService {
    void insertComment(VideoCommentVO videoCommentVO);
    
 // 추가) 특정 영상의 댓글 목록 가져오기
    List<VideoCommentVO> selectCommentsByVideoId(Long videoId);
}
