package kr.spring.video.service;

import java.util.List;
import java.util.Map;

import kr.spring.video.vo.VideoVO;

public interface VideoService {
    List<VideoVO> selectList(Map<String, Object> map);
    Integer selectRowCount(Map<String, Object> map);
    void insertVideo(VideoVO video);
    VideoVO selectVideo(Long videoId);
    void updateHit(Long videoId);
    void updateVideo(VideoVO video);
    void deleteVideo(Long videoId);
    Long getNextVideoId();
	List<VideoVO> selectListByGroup(Map<String, Object> map);
	int selectRowCountByGroup(Map<String, Object> map);
	List<VideoVO> getVideosByCategoryAndGroup(Long category_id, Long groupNum);
	List<VideoVO> getMembershipVideosByGroup(Long groupNum);
}
