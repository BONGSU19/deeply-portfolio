package kr.spring.video.service;

import java.util.List;

import kr.spring.video.vo.VideoCategoryVO;

public interface VideoCategoryService {
    VideoCategoryVO addCategory(Long artistId, String categoryName); // 새 카테고리 추가
	List<VideoCategoryVO> getCategoriesByGroupNum(Long groupNum);
	Long getGroupNumByUserNum(Long userNum);
}
