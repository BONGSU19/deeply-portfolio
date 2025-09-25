package kr.spring.faq.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FAQCategoryVO {
	private int categoryId; // FAQ 유형 ID
    private String categoryName; // FAQ 유형명
    private String description; // 설명추가
}
