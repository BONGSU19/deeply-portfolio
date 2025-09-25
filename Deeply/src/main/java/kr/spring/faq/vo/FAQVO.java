package kr.spring.faq.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FAQVO {
	private int faqId; // FAQ ID
    private int categoryId; // FAQ 유형 ID
    private String question; // 질문
    private String answer; // 답변
    private Timestamp createdAt; // 등록 일시
}
