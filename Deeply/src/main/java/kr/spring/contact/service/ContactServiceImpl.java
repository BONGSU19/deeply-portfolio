package kr.spring.contact.service;

import java.util.List;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.contact.dao.ContactMapper;
import kr.spring.contact.vo.ContactVO;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public void createContact(ContactVO contact) {
        contact.setStatus(0); // 기본 상태: 대기중
        contactMapper.insertContact(contact);
    }

    @Override
    public List<ContactVO> getAllContacts() {
        return contactMapper.selectAllContacts();
    }

    @Override
    public ContactVO getContactById(int inquiryId) {
        return contactMapper.selectContactById(inquiryId);
    }

    @Override
    public void updateStatus(ContactVO contact) {
        contactMapper.updateContactStatus(contact);
    }

    @Override
    public void respondToContact(ContactVO contact) {
        contact.setAnsweredAt(new Timestamp(System.currentTimeMillis()));
        contact.setStatus(1); // 답변완료
        contactMapper.updateAdminResponse(contact);
    }

    @Override
    public void removeContact(int inquiryId) {
        contactMapper.deleteContact(inquiryId);
    }

    @Override
    public List<ContactVO> searchContacts(ContactVO searchParams) {
        return contactMapper.searchContacts(searchParams);
    }
}
