package kr.spring.video.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.video.vo.VideoVO;

@Mapper
public interface VideoMapper {
	
	public List<VideoVO> selectList(Map<String, Object> map);

	@Select("SELECT COUNT(*) FROM video")
	public Integer selectRowCount(Map<String, Object> map);

	@Select("SELECT video_id AS videoId, artist_id AS artistId, title, description, is_exclusive AS isExclusive, " +
            "media_url AS mediaUrl, created_at AS createdAt, views, likes, comments_count AS commentsCount, " +
            "category_id AS categoryId, group_num AS groupNum FROM video WHERE video_id = #{videoId}")
    public VideoVO selectVideo(@Param("videoId") Long videoId);

	public void insertVideo(VideoVO video);

	@Update("UPDATE video SET views = views + 1 WHERE video_id = #{videoId}")
	public void updateHit(Long videoId);

	@Update("UPDATE video SET title = #{title}, description = #{description}, is_exclusive = #{isExclusive}, media_url = #{mediaUrl}, category_id = #{categoryId} WHERE video_id = #{videoId}")
	public void updateVideo(VideoVO video);

	@Delete("DELETE FROM video WHERE video_id = #{videoId}")
	public void deleteVideo(Long videoId);

	@Select("SELECT VIDEO_SEQ.NEXTVAL FROM dual")
	Long getNextVideoId();

	List<VideoVO> selectListByGroup(Map<String, Object> map);

	@Select("SELECT COUNT(*) FROM video WHERE group_num = #{groupNum}")
		    Integer selectRowCountByGroup(Map<String, Object> map);

		List<VideoVO> selectVideosByCategoryAndGroup(
		    @Param("categoryId") Long categoryId,
		    @Param("groupNum") Long groupNum
		);

		 // 멤버십 전용 영상 조회
	    List<VideoVO> selectMembershipVideosByGroup(Long groupNum);

}
