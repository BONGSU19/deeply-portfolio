package kr.spring.faq.controller;

import kr.spring.faq.service.FAQService;

import kr.spring.faq.vo.FAQVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.faq.vo.FAQCategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/faq")
public class FAQController {
	
	@Autowired
    private final FAQService faqService;

    public FAQController(FAQService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public String listFAQs(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<FAQVO> faqs = faqService.getAllFAQs();
        List<FAQCategoryVO> categories = faqService.getAllCategories();
        model.addAttribute("faqs", faqs);
        model.addAttribute("categories", categories);

        if (principalDetails != null && principalDetails.hasRole("ADMIN")) {
            model.addAttribute("isAdmin", true);
        }else {
            model.addAttribute("isAdmin", false);
        }

        return "faq"; // /WEB-INF/views/faq.jsp
    }


    // FAQ 추가 폼
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String addFAQForm(Model model) {
        List<FAQCategoryVO> categories = faqService.getAllCategories();
        model.addAttribute("categories", categories);
        return "addFaq"; // /WEB-INF/views/add_faq.jsp
    }

    // FAQ 추가 처리
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addFAQ(@ModelAttribute FAQVO faq) {
        faqService.addFAQ(faq);
        return "redirect:/faq";
    }

    // FAQ 삭제 처리
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{faqId}")
    public String deleteFAQ(@PathVariable int faqId) {
        faqService.deleteFAQ(faqId);
        return "redirect:/faq";
    }

    // FAQ 수정 폼
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{faqId}")
    public String editFAQForm(@PathVariable int faqId, Model model) {
        FAQVO faq = faqService.getFAQById(faqId);
        List<FAQCategoryVO> categories = faqService.getAllCategories();
        model.addAttribute("faq", faq);
        model.addAttribute("categories", categories);
        return "editFaq"; // /WEB-INF/views/edit_faq.jsp
    }

    // FAQ 수정 처리
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit")
    public String editFAQ(@ModelAttribute FAQVO faq) {
        faqService.updateFAQ(faq);
        return "redirect:/faq";
    }

    // 카테고리 관리 페이지
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/categories")
    public String manageCategories(Model model) {
        List<FAQCategoryVO> categories = faqService.getAllCategories();
        model.addAttribute("categories", categories);
        return "manageCategories"; // /WEB-INF/views/manage_categories.jsp
    }

    // 카테고리 추가 폼
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/categories/add")
    public String addCategoryForm() {
        return "addCategory"; // /WEB-INF/views/add_category.jsp
    }

    // 카테고리 추가 처리
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute FAQCategoryVO category) {
        faqService.addCategory(category);
        return "redirect:/faq/categories";
    }

    // 카테고리 삭제 처리
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/categories/delete/{categoryId}")
    public String deleteCategory(@PathVariable int categoryId) {
        faqService.deleteCategory(categoryId);
        return "redirect:/faq/categories";
    }

    // 카테고리 수정 폼
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/categories/edit/{categoryId}")
    public String editCategoryForm(@PathVariable int categoryId, Model model) {
        FAQCategoryVO category = faqService.getCategoryById(categoryId);
        model.addAttribute("category", category);
        return "editCategory"; // /WEB-INF/views/edit_category.jsp
    }

    // 카테고리 수정 처리
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categories/edit")
    public String editCategory(@ModelAttribute FAQCategoryVO category) {
        faqService.updateCategory(category);
        return "redirect:/faq/categories";
    }
}
