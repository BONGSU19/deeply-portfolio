package kr.spring.video.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VideoCategoryVO {
    private Long category_id;      // 카테고리 ID (PK)
    private Long user_num;         // 아티스트 번호 (FK)
    private String category_name;  // 카테고리 이름
}
