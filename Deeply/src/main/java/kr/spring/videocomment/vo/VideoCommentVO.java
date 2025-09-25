package kr.spring.videocomment.vo;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VideoCommentVO {
    private Long commentId;          // 댓글 고유 ID
    private Long userNum;            // 유저 ID (DUSER_DETAIL 테이블의 user_num 참조)
    private Long videoId;            // 영상 ID (video 테이블 참조)
    private Long parentCommentId;    // 상위 댓글 ID (대댓글인 경우)
    private String commentContent;   // 댓글 내용
    private Timestamp createdAt;     // 댓글 작성 시점
    private String userName;
}
