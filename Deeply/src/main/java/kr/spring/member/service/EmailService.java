package kr.spring.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;  // JavaMailSender를 주입받습니다.

    @Autowired
    private MemberService memberService;

    public String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 1000000)); // 6자리 숫자 코드
    }

    public void sendVerificationEmail(String email, String verificationCode) {
        // 이메일 전송 로직 추가
        System.out.println("To: " + email);
        System.out.println("Verification Code: " + verificationCode);

        // 인증 코드 저장
        memberService.storeVerificationCode(email, verificationCode);
        
        
        // 실제 이메일 전송
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            // 이메일 발송 설정
            helper.setFrom("samsun19seo@gmail.com"); // 보내는 이메일 주소
            helper.setTo(email); // 받는 이메일 주소
            helper.setSubject("이메일 인증 코드"); // 이메일 제목
            helper.setText("인증 코드: " + verificationCode); // 이메일 내용

            // 이메일 전송
            mailSender.send(message);
            System.out.println("이메일이 성공적으로 전송되었습니다.");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("이메일 전송에 실패했습니다.");
        }
    }
    
    public void sendPasswordResetEmail(String email) {
        String resetLink = "http://localhost:8080/member/resetPassword?email=" + email;
        
        // 이메일 전송 설정
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            // 이메일 발송 설정
            helper.setFrom("samsun19seo@gmail.com"); // 보내는 이메일 주소
            helper.setTo(email); // 받는 이메일 주소
            helper.setSubject("비밀번호 재설정 링크"); // 이메일 제목
            helper.setText("비밀번호를 재설정하려면 아래 링크를 클릭하세요:\n" + resetLink); // 이메일 내용

            // 이메일 전송
            mailSender.send(message);
            System.out.println("비밀번호 재설정 이메일이 성공적으로 전송되었습니다.");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("비밀번호 재설정 이메일 전송에 실패했습니다.");
        }
    }
}
