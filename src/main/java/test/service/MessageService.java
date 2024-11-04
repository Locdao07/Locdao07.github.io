package test.service;

import java.util.List;

import test.entity.MessageDTO;

public interface MessageService {
	 MessageDTO sendMessage(MessageDTO messageDTO);
	    List<MessageDTO> getMessagesForStudent(Long studentId);
	    List<MessageDTO> getReceivedMessagesByStudent(Long studentId);
	    List<MessageDTO> getSentMessagesByStudent(Long studentId);
	    List<MessageDTO> getDeletedMessagesByStudent(Long studentId);
		boolean deleteMessage(Long id);
		 MessageDTO getMessageById(Long id);
		 
		// Long countUnreadMessages(Long studentId);
		 
    
}

	