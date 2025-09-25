package kr.spring.faq.dao;

import kr.spring.faq.vo.FAQVO;
import kr.spring.faq.vo.FAQCategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FAQMapper {
    // FAQ 관련 메서드
    List<FAQVO> selectAllFAQs();
    FAQVO selectFAQById(int faqId);
    void insertFAQ(FAQVO faq);
    void updateFAQ(FAQVO faq);
    void deleteFAQ(int faqId);
    int getNextFAQId();

    // FAQ Category 관련 메서드
    List<FAQCategoryVO> selectAllCategories();
    FAQCategoryVO selectCategoryById(int categoryId);
    void insertCategory(FAQCategoryVO category);
    void updateCategory(FAQCategoryVO category);
    void deleteCategory(int categoryId);
    int getNextCategoryId();
}
