package kr.spring.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticVO {
    private String record_date;  // 날짜는 String으로 처리
    private int active_users;
}
