package kr.spring.faq.service;

import kr.spring.faq.dao.FAQMapper;
import kr.spring.faq.vo.FAQVO;
import kr.spring.faq.vo.FAQCategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FAQServiceImpl implements FAQService {

    private final FAQMapper faqMapper;

    public FAQServiceImpl(FAQMapper faqMapper) {
        this.faqMapper = faqMapper;
    }

    // FAQ 관련 메서드 구현
    @Override
    public List<FAQVO> getAllFAQs() {
        return faqMapper.selectAllFAQs();
    }

    @Override
    public FAQVO getFAQById(int faqId) {
        return faqMapper.selectFAQById(faqId);
    }

    @Override
    @Transactional
    public void addFAQ(FAQVO faq) {
        faqMapper.insertFAQ(faq);
        log.info("Added new FAQ with ID: {}", faq.getFaqId());
    }

    @Override
    @Transactional
    public void updateFAQ(FAQVO faq) {
        faqMapper.updateFAQ(faq);
        log.info("Updated FAQ with ID: {}", faq.getFaqId());
    }

    @Override
    @Transactional
    public void deleteFAQ(int faqId) {
        faqMapper.deleteFAQ(faqId);
        log.info("Deleted FAQ with ID: {}", faqId);
    }

    // FAQ Category 관련 메서드 구현
    @Override
    public List<FAQCategoryVO> getAllCategories() {
        return faqMapper.selectAllCategories();
    }

    @Override
    public FAQCategoryVO getCategoryById(int categoryId) {
        return faqMapper.selectCategoryById(categoryId);
    }

    @Override
    @Transactional
    public void addCategory(FAQCategoryVO category) {
        faqMapper.insertCategory(category);
        log.info("Added new FAQ Category with ID: {}", category.getCategoryId());
    }

    @Override
    @Transactional
    public void updateCategory(FAQCategoryVO category) {
        faqMapper.updateCategory(category);
        log.info("Updated FAQ Category with ID: {}", category.getCategoryId());
    }

    @Override
    @Transactional
    public void deleteCategory(int categoryId) {
        faqMapper.deleteCategory(categoryId);
        log.info("Deleted FAQ Category with ID: {}", categoryId);
    }
}
