package kr.spring.faq.service;

import java.util.List;
import java.util.Map;

import kr.spring.faq.vo.FAQCategoryVO;
import kr.spring.faq.vo.FAQVO;
import kr.spring.video.vo.VideoVO;

public interface FAQService {
	// FAQ 관련 메서드
    List<FAQVO> getAllFAQs();
    FAQVO getFAQById(int faqId);
    void addFAQ(FAQVO faq);
    void updateFAQ(FAQVO faq);
    void deleteFAQ(int faqId);

    // FAQ Category 관련 메서드
    List<FAQCategoryVO> getAllCategories();
    FAQCategoryVO getCategoryById(int categoryId);
    void addCategory(FAQCategoryVO category);
    void updateCategory(FAQCategoryVO category);
    void deleteCategory(int categoryId);
}
