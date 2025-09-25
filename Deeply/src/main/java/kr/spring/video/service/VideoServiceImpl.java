package kr.spring.video.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.video.dao.VideoMapper;
import kr.spring.video.vo.VideoVO;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<VideoVO> selectList(Map<String, Object> map) {
        return videoMapper.selectList(map);
    }

    @Override
    public Integer selectRowCount(Map<String, Object> map) {
        return videoMapper.selectRowCount(map);
    }

    @Override
    public void insertVideo(VideoVO video) {
        videoMapper.insertVideo(video);
    }

    @Override
    public VideoVO selectVideo(Long videoId) {
        return videoMapper.selectVideo(videoId);
    }

    @Override
    public void updateHit(Long videoId) {
        videoMapper.updateHit(videoId);
    }

    @Override
    public void updateVideo(VideoVO video) {
        videoMapper.updateVideo(video);
    }

    @Override
    public void deleteVideo(Long videoId) {
        videoMapper.deleteVideo(videoId);
    }
    
    @Override
    public Long getNextVideoId() {
        return videoMapper.getNextVideoId();
    }

	@Override
	public List<VideoVO> selectListByGroup(Map<String, Object> map) {
		return videoMapper.selectListByGroup(map);
	}

	@Override
	public int selectRowCountByGroup(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<VideoVO> getVideosByCategoryAndGroup(Long category_id, Long groupNum) {
		 return videoMapper.selectVideosByCategoryAndGroup(category_id, groupNum);
	}

	@Override
    public List<VideoVO> getMembershipVideosByGroup(Long groupNum) {
        return videoMapper.selectMembershipVideosByGroup(groupNum);
    }
}
