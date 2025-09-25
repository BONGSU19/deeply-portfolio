package kr.spring.videocomment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.videocomment.vo.VideoCommentVO;

@Mapper
public interface VideoCommentMapper {
	
	@Insert("INSERT INTO video_comments (comment_id, video_id, user_num, comment_content, parent_comment_id) " +
            "VALUES (video_comments_seq.NEXTVAL, #{videoId}, #{userNum}, #{commentContent}, #{parentCommentId})")
    void insertComment(VideoCommentVO commentVO);
	
	// 2) 특정 videoId의 댓글 목록 조회
	@Select("SELECT "
	          + "  c.comment_id AS commentId, "
	          + "  c.user_num AS userNum, "
	          + "  c.video_id AS videoId, "
	          + "  c.parent_comment_id AS parentCommentId, "
	          + "  c.comment_content AS commentContent, "
	          + "  c.created_at AS createdAt, "
	          + "  NVL(a.name, d.nick_name) AS userName "
	          + "FROM video_comments c "
	          + "LEFT JOIN auser_detail a ON c.user_num = a.user_num "
	          + "LEFT JOIN duser d ON c.user_num = d.user_num "
	          + "WHERE c.video_id = #{videoId} "
	          + "ORDER BY c.comment_id DESC")
	List<VideoCommentVO> selectCommentsByVideoId(Long videoId);
	
}
