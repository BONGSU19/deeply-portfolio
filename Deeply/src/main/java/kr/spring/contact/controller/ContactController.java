package kr.spring.contact.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import kr.spring.contact.service.ContactService;
import kr.spring.contact.vo.ContactVO;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    // 사용자: 문의 작성 폼
    @GetMapping("/inquiryForm")
    public String showInquiryForm() {
        return "inquiryForm"; // Tiles 정의 이름과 일치
    }

    // 사용자: 문의 작성 처리
    @PostMapping("/submitInquiry")
    public String submitInquiry(@ModelAttribute ContactVO contact,
                                @RequestParam("file") MultipartFile file,
                                HttpSession session) {
        // 사용자 ID 설정 (로그인 구현 가정)
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 로그인되지 않은 경우 처리 (예: 로그인 페이지로 리다이렉트)
            return "redirect:/login?error=not_logged_in";
        }
        contact.setUserNum(userId); // user_num 설정

        // 파일 업로드 처리
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            // 실제 파일 저장 로직 필요 (예: 서버의 특정 디렉토리에 저장)
            // 예시: /uploads 디렉토리에 저장
            String uploadDir = "/path/to/uploads/"; // 실제 경로로 변경 필요
            try {
                file.transferTo(new java.io.File(uploadDir + fileName));
                contact.setFileName(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                // 파일 업로드 실패 시 처리 (예: 에러 메시지 추가)
                return "redirect:/contact/inquiryForm?error=upload_failed";
            }
        } else {
            contact.setFileName("없음");
        }

        // UPDATED_AT은 생성 시점이므로 별도 설정 필요 없음
        contactService.createContact(contact);
        return "redirect:/contact/inquiryForm?success=true";
    }

    // 관리자: 모든 문의 목록 보기
    @GetMapping("/admin/list")
    public String listContacts(@RequestParam(value = "category", required = false) String category,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               Model model) {
        ContactVO searchParams = new ContactVO();
        searchParams.setTitle(category); // 카테고리 설정 (타이틀 컬럼과 매핑)
        searchParams.setContent(keyword); // 키워드 설정

        if (category != null && keyword != null && !keyword.isEmpty()) {
            model.addAttribute("contacts", contactService.searchContacts(searchParams));
        } else {
            model.addAttribute("contacts", contactService.getAllContacts());
        }
        return "adminInquiryList"; // Tiles 정의 이름과 일치
    }

    // 관리자: 특정 문의 상세 보기
    @GetMapping("/admin/view")
    public String viewContact(@RequestParam("id") int inquiryId, Model model) {
        ContactVO contact = contactService.getContactById(inquiryId);
        model.addAttribute("contact", contact);
        return "adminViewInquiry"; // Tiles 정의 이름과 일치
    }

    // 관리자: 문의 상태 업데이트
    @PostMapping("/admin/updateStatus")
    public String updateStatus(@RequestParam("id") int inquiryId,
                               @RequestParam("status") int status) {
        ContactVO contact = new ContactVO();
        contact.setInquiryId(inquiryId);
        contact.setStatus(status);
        contactService.updateStatus(contact);
        return "redirect:/contact/admin/list";
    }

    // 관리자: 문의에 답변하기
    @PostMapping("/admin/respond")
    public String respondToContact(@RequestParam("id") int inquiryId,
                                   @RequestParam("response") String response) {
        ContactVO contact = new ContactVO();
        contact.setInquiryId(inquiryId);
        contact.setAnswerContent(response);
        contactService.respondToContact(contact);
        return "redirect:/contact/admin/view?id=" + inquiryId;
    }

    // 관리자: 문의 삭제
    @PostMapping("/admin/delete")
    public String deleteContact(@RequestParam("id") int inquiryId) {
        contactService.removeContact(inquiryId);
        return "redirect:/contact/admin/list";
    }
}
