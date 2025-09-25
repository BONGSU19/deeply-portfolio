package kr.spring.contact.service;

import java.util.List;
import kr.spring.contact.vo.ContactVO;

public interface ContactService {
    void createContact(ContactVO contact);

    List<ContactVO> getAllContacts();

    ContactVO getContactById(int inquiryId);

    void updateStatus(ContactVO contact);

    void respondToContact(ContactVO contact);

    void removeContact(int inquiryId);

    List<ContactVO> searchContacts(ContactVO searchParams);
}
