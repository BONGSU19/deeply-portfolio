package kr.spring.video.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.vo.PrincipalDetails;
import kr.spring.video.service.VideoCategoryService;
import kr.spring.video.vo.VideoCategoryVO;

@Controller
@RequestMapping("/categories")
public class VideoCategoryController {
    
    @Autowired
    private VideoCategoryService videoCategoryService;

    @GetMapping("/list")
    @ResponseBody
    public List<VideoCategoryVO> listCategories(@AuthenticationPrincipal PrincipalDetails principal) {
        // 로그인한 사용자의 user_num으로 group_num 가져오기
        Long userNum = principal.getArtistVO().getUser_num();
        Long groupNum = videoCategoryService.getGroupNumByUserNum(userNum); // group_num 추출

        if (groupNum == null) {
            throw new IllegalStateException("사용자의 그룹 정보가 없습니다.");
        }

        // group_num으로 카테고리 조회
        List<VideoCategoryVO> categories = videoCategoryService.getCategoriesByGroupNum(groupNum);

        // 디버깅용 로그 출력
        System.out.println("GroupNum: " + groupNum + ", Categories: " + categories);

        return categories;
    }



    // 새로운 카테고리 추가
    @PostMapping("/add")
    @ResponseBody
    public VideoCategoryVO addCategory(@RequestParam String categoryName,
                                       @AuthenticationPrincipal PrincipalDetails principal) {
        Long artistId = principal.getArtistVO().getUser_num();
        VideoCategoryVO category = videoCategoryService.addCategory(artistId, categoryName);
        return category; // 새로 추가된 카테고리 반환
    }
}
	
