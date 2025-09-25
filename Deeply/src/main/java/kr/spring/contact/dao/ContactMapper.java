package kr.spring.contact.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.spring.contact.vo.ContactVO;

@Mapper
public interface ContactMapper {
    void insertContact(ContactVO contact);

    List<ContactVO> selectAllContacts();

    ContactVO selectContactById(int inquiryId);

    void updateContactStatus(ContactVO contact);

    void updateAdminResponse(ContactVO contact);

    void deleteContact(int inquiryId);

    List<ContactVO> searchContacts(ContactVO searchParams);
}
