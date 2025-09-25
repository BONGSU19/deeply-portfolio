package kr.spring.video.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.member.dao.ArtistMapper;
import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.payment.controller.FanController;
import kr.spring.payment.service.FanService;
import kr.spring.payment.vo.FanVO;
import kr.spring.video.service.VideoCategoryService;
import kr.spring.video.service.VideoService;
import kr.spring.video.vo.VideoCategoryVO;
import kr.spring.video.vo.VideoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j // Lombok 어노테이션 추가
@Controller
@RequestMapping("/artist/videos") // 기본 경로를 /artist/videos로 설정
public class VideoController {

    @Autowired
    private VideoService videoService;
    
    @Autowired
    private VideoCategoryService videoCategoryService;
    
    @Autowired
    private FanService fanService; // FanService 주입
    
    @Autowired
    private ArtistMapper artistMapper;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/group")
    public String showGroupVideosPage(
        @RequestParam("group_num") Long groupNum,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @AuthenticationPrincipal PrincipalDetails principal,
        Model model
    ) {
        // 사용자 역할 체크
        ArtistVO artistVO = principal.getArtistVO();
        if (artistVO != null && artistVO.getUser_num() != null) {
            model.addAttribute("isArtist", true);
            model.addAttribute("isMembership", true);
        } else if (principal.hasRole("ADMIN")) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isGeneralUser", true); // 일반 사용자 처리
        }

     // 멤버십 상태 체크
        if (artistVO == null && !principal.hasRole("ADMIN")) {
            MemberVO memberVO = principal.getMemberVO();
            if (memberVO != null) {
                Long userNum = memberVO.getUser_num();
                
                
                // 그룹 소속 아티스트 목록 조회
                List<ArtistVO> artists = artistMapper.selectGroupMembers(groupNum);
                
                boolean isMembership = false;
                if (artists != null && !artists.isEmpty()) {
                    for (ArtistVO artist : artists) {
                        // 각 아티스트별로 팬 상태 확인
                        FanVO fanVO = fanService.selectFan(userNum, artist.getUser_num());
                        if (fanVO != null && fanVO.getFan_status() > 0) {
                            isMembership = true;
                            break; // 하나라도 조건 충족 시 탈출
                        }
                    }
                }
                
                model.addAttribute("isMembership", isMembership);
            } else {
                model.addAttribute("isMembership", false);
            }
        }

        // 1. group_num으로 카테고리 조회
        List<VideoCategoryVO> categories = videoCategoryService.getCategoriesByGroupNum(groupNum);
        categories.forEach(category -> System.out.println("Category: " + category));

        // 2. 카테고리별 영상 조회
        Map<Long, List<VideoVO>> categoryVideosMap = new HashMap<>();
        for (VideoCategoryVO category : categories) {
            List<VideoVO> videos = videoService.getVideosByCategoryAndGroup(category.getCategory_id(), groupNum);
            System.out.println("Category ID: " + category.getCategory_id() + " -> Videos count: " + videos.size());
            videos.forEach(video -> System.out.println("Video: " + video));

            categoryVideosMap.put(category.getCategory_id(), videos);
        }

        // 3. 멤버십 전용 영상 조회
        List<VideoVO> membershipVideos = videoService.getMembershipVideosByGroup(groupNum);
        membershipVideos.forEach(video -> System.out.println("Membership Video: " + video));

        // 4. 카테고리, 영상 데이터, groupNum을 JSP로 전달
        model.addAttribute("categories", categories); // 카테고리 목록
        model.addAttribute("categoryVideosMap", categoryVideosMap); // 카테고리별 영상
        model.addAttribute("membershipVideos", membershipVideos); // 멤버십 영상 목록
        model.addAttribute("groupNum", groupNum); // 그룹 번호

        return "groupVideoList"; // JSP 파일 이름
    }


 // 영상 업로드 페이지 (로그인 필요)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/upload_form")
    public String showUploadPage(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("==> showUploadPage called");
        return "videoUpload"; // Tiles 정의 이름
    }

    // 영상 업로드 처리 (로그인 필요)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload")
    public String uploadVideo(
        VideoVO video,
        @RequestParam(value = "mediaUrl", required = false) String mediaUrl,
        @RequestParam(value = "isExclusive", defaultValue = "0") int isExclusive,
        @AuthenticationPrincipal PrincipalDetails principal) {

        System.out.println("==> uploadVideo called with mediaUrl: " + mediaUrl);

        // 1) 로그인 사용자 정보 가져오기
        ArtistVO artist = principal.getArtistVO();
        System.out.println("Logged-in artist: " + artist);

        // 2) group_name으로 agroup 정보(AgroupVO) 조회
        AgroupVO agroup = artistMapper.findByGroupName(artist.getGroup_name());
        if (agroup == null) {
            throw new IllegalArgumentException("해당 group_name이 agroup 테이블에 존재하지 않습니다.");
        }
        System.out.println("Agroup: " + agroup);

        // 3) video에 필요한 값 채우기
        video.setArtistId(artist.getUser_num());
        video.setGroupNum(agroup.getGroup_num());
        video.setIsExclusive(isExclusive);

        // mediaUrl 설정
        if (mediaUrl != null && !mediaUrl.trim().isEmpty()) {
            video.setMediaUrl(mediaUrl.trim());
        } else {
            video.setMediaUrl("");
        }
        System.out.println("Final video object before save: " + video);

        // 4) DB에 영상 정보 저장
        videoService.insertVideo(video);

        return "redirect:/artist/videos/group?group_num=" + agroup.getGroup_num();
    }

    // GET /artist/videos/page?videoId=37&group_num=61 (로그인 필요)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/page")
    public String showVideoPage(
        @RequestParam("videoId") Long videoId,
        @RequestParam("group_num") Long groupNum,
        @AuthenticationPrincipal PrincipalDetails principal,
        Model model
    ) {
        log.info("==> showVideoPage called with videoId: {}, groupNum: {}", videoId, groupNum);
        
        Long userNum;
        
        // 사용자 역할 체크
        ArtistVO artistVO = principal.getArtistVO();
        if (artistVO != null && artistVO.getUser_num() != null) {
            model.addAttribute("isArtist", true);
            userNum = principal.getArtistVO().getUser_num();
        } else if (principal.hasRole("ADMIN")) {
            model.addAttribute("isAdmin", true);
            userNum = principal.getMemberVO().getUser_num();
        } else {
            model.addAttribute("isGeneralUser", true); // 일반 사용자 처리
            userNum = principal.getMemberVO().getUser_num();
        }
        
        

        // 영상 정보 조회
        VideoVO video = videoService.selectVideo(videoId);

        // YouTube URL 변환
        String youtubeLink = convertToEmbedUrl(video.getMediaUrl());

        // 데이터 모델에 추가
        model.addAttribute("youtubeLink", youtubeLink); // 유튜브 링크
        model.addAttribute("video", video);            // 영상 정보
        model.addAttribute("title", video.getTitle()); 
        model.addAttribute("groupNum", groupNum);      // 그룹 번호
        model.addAttribute("userNum", userNum);        // 로그인 사용자 번호

        return "videoPage"; // JSP 파일 이름
    }

    /**
     * 일반 YouTube URL을 embed URL로 변환
     * @param youtubeUrl 일반 YouTube URL (e.g., https://youtu.be/abc123, https://www.youtube.com/watch?v=abc123)
     * @return embed URL (e.g., https://www.youtube.com/embed/abc123)
     */
    private String convertToEmbedUrl(String youtubeUrl) {
        if (youtubeUrl == null || youtubeUrl.isEmpty()) {
            return "";
        }

        String videoId = youtubeUrl.replaceAll(".*(?:v=|youtu\\.be/|embed/)([^&?/]+).*", "$1");
        if (videoId.equals(youtubeUrl)) { // 변환 실패 시 원본 URL 반환
            log.warn("Unable to extract video ID from URL: {}", youtubeUrl);
            return youtubeUrl;
        }
        return "https://www.youtube.com/embed/" + videoId;
    }

    // 임시 테스트 메서드
    @GetMapping("/test")
    public String testPage(Model model) {
        model.addAttribute("message", "Test Page Loaded");
        return "video/test"; // JSP 파일 이름
    }
}
