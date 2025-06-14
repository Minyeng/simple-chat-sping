package spring.simple_chat_sping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.simple_chat_sping.entity.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    boolean existsBySender(String sender);
    List<Message> findAllBySenderOrderByDatetimeDesc(String sender);
}
