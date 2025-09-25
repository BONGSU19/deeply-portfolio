package kr.spring.video.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.video.dao.VideoCategoryMapper;
import kr.spring.video.vo.VideoCategoryVO;

@Service
public class VideoCategoryServiceImpl implements VideoCategoryService {

    @Autowired
    private VideoCategoryMapper videoCategoryMapper;

    @Override
    public List<VideoCategoryVO> getCategoriesByGroupNum(Long groupNum) {
        // Mapper 메서드 호출
        return videoCategoryMapper.selectCategoriesByGroupNum(groupNum);
    }

    @Override
    public VideoCategoryVO addCategory(Long artistId, String categoryName) {
        // 새로운 카테고리 추가
        VideoCategoryVO category = new VideoCategoryVO();
        category.setUser_num(artistId);
        category.setCategory_name(categoryName);
        
        videoCategoryMapper.insertCategory(category);
        
        // 새로 추가된 카테고리를 반환
        return category;
    }

	@Override
	public Long getGroupNumByUserNum(Long userNum) {
		 return videoCategoryMapper.findGroupNumByUserNum(userNum);
	}
}
