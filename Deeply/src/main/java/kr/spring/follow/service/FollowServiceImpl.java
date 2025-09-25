package kr.spring.follow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.follow.dao.FollowMapper;
import kr.spring.follow.vo.FollowVO;

@Service
@Transactional
public class FollowServiceImpl implements FollowService{
	@Autowired
	FollowMapper followMapper;

	@Override
	public List<FollowVO> getMyFollow(Map<String, Object> map) {
		return followMapper.getMyFollow(map);
	}

	@Override
	public Integer countMyFollow(long follower_num) {
		return followMapper.countMyFollow(follower_num);
	}

	@Override
	public FollowVO selectFollow(FollowVO follow) {
		return followMapper.selectFollow(follow);
	}

	@Override
	public void following(FollowVO follow) {
		followMapper.following(follow);
	}

	@Override
	public void unfollow(FollowVO follow) {
		followMapper.unfollow(follow);
	}

	@Override
	public Integer countMyFollower(long follow_num) {
		return followMapper.countMyFollower(follow_num);
	}
	
}