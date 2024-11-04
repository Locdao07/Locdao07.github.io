package test.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.entity.Message;
import test.entity.Student;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	     
	List<Message> findBySenderIdOrReceiverIdOrderByTimestampDesc(Long senderId, Long receiverId);
    List<Message> findByReceiverIdOrderByTimestampDesc(Long receiverId);
    List<Message> findBySenderIdOrderByTimestampDesc(Long senderId);
    List<Message> findByReceiverIdAndStatusOrderByTimestampDesc(Long receiverId, String status);
    List<Message> findBySenderIdAndStatusOrderByTimestampDesc(Long senderId, String status);
   // Long countByReceiverIdAndIsReadFalse(Long receiverId);

   
}

