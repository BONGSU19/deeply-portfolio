package kr.spring.video.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VideoVO {
    private Long videoId;         // 영상 ID
    private Long artistId;        // 아티스트 ID
    private String title;         // 영상 제목
    private String description;   // 영상 설명
    private Integer isExclusive;  // 멤버십 여부 (0: 일반, 1: 멤버십 전용)
    private String mediaUrl;      // 영상 URL
    private Timestamp createdAt;  // 생성일자
    private Integer views;        // 조회수
    private Integer likes;        // 좋아요 수
    private Integer commentsCount;// 댓글 수
    private Long categoryId;      // 카테고리 ID
    private Long groupNum;        // 그룹 번호 (AUSER_DETAIL.group_num 등과 연동)
}
