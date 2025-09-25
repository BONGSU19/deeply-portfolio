package kr.spring.contact.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ContactVO {
	private int inquiryId;
    private int userNum;
    private String title;
    private String content;
    private int status; // 0: 대기중, 1: 답변완료, 2: 처리중
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;
    private String answerContent;
    private java.sql.Timestamp answeredAt;
    private String fileName;
}
