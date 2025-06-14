package spring.simple_chat_sping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.simple_chat_sping.entity.Contact;
import java.util.List;
import java.util.Optional;




@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    boolean existsByUsername(String username);
    List<Contact> findAllByUsernameOrderByIdAsc(String username);
    Optional<Contact> findByUsernameAndContactUsername(String username, String contactUsername);
}
