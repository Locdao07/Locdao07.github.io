package test.Server.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.Repository.MessageRepository;
import test.Repository.StudentRepository;
import test.entity.Message;
import test.entity.MessageDTO;
import test.entity.Student;
import test.service.MessageService;
import test.service.StudentService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Override
    public List<MessageDTO> getMessagesForStudent(Long studentId) {
        List<Message> messages = messageRepository.findBySenderIdOrReceiverIdOrderByTimestampDesc(studentId, studentId);
        return groupAndConvertToDTO(messages);
    }


    @Override
    public List<MessageDTO> getReceivedMessagesByStudent(Long studentId) {
        List<Message> messages = messageRepository.findByReceiverIdOrderByTimestampDesc(studentId);
        //return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
        return groupAndConvertToDTO(messages);
    }

    @Override
    public List<MessageDTO> getSentMessagesByStudent(Long studentId) {
        List<Message> messages = messageRepository.findBySenderIdOrderByTimestampDesc(studentId);
        return groupAndConvertToDTO(messages);
    }
    @Override
    public MessageDTO sendMessage(MessageDTO messageDTO) {
        Student sender = studentService.getStudentById(messageDTO.getSenderId());
        String[] receiverEmails = messageDTO.getReceiverEmail().split(",");

        List<Message> messagesToSave = new ArrayList<>();

        for (String email : receiverEmails) {
            email = email.trim();
            Student receiver = studentRepository.findByEmail(email);

            if (receiver != null) {
                Message message = new Message();
                message.setSender(sender);
                message.setReceiver(receiver);
                message.setContent(messageDTO.getContent());
                message.setTimestamp(LocalDateTime.now());
                message.setStatus("sent");
                message.setDeleted(false);

                messagesToSave.add(message);
            } else {
                throw new RuntimeException("Receiver not found: " + email);
            }
        }

        // Save all messages at once
        messageRepository.saveAll(messagesToSave);
		return messageDTO;
    }

    private List<MessageDTO> groupAndConvertToDTO(List<Message> messages) {
        Map<String, MessageDTO> messageMap = new LinkedHashMap<>();

        for (Message message : messages) {
            String key = message.getContent() + message.getTimestamp(); // Key để nhóm các tin nhắn
            MessageDTO messageDTO = messageMap.get(key);

            if (messageDTO == null) {
                messageDTO = convertToDTO(message);
                messageDTO.setReceiverNames(new ArrayList<>()); // Khởi tạo danh sách receiverNames
                messageMap.put(key, messageDTO);
            }

            // Thêm tên người nhận vào danh sách
            String receiverName = message.getReceiver().getFirstName() + " " + message.getReceiver().getLastName();
            if (!messageDTO.getReceiverNames().contains(receiverName)) {
                messageDTO.getReceiverNames().add(receiverName);
            }
        }

        // Format receiver names
        for (MessageDTO dto : messageMap.values()) {
            List<String> receiverNames = dto.getReceiverNames();
            if (receiverNames.size() > 2) {
                dto.setReceiverNamesFormatted(receiverNames.get(0) + ", " + receiverNames.get(1) + " , [" + (receiverNames.size() - 2) + "... ]");
            } else {
                dto.setReceiverNamesFormatted(String.join(", ", receiverNames));
            }
            if (dto.getContent().length() > 50) {
                dto.setShortContent(dto.getContent().substring(0, 50) + "...");
            } else {
                dto.setShortContent(dto.getContent());
            }
        }

        return new ArrayList<>(messageMap.values());
    }

    private MessageDTO convertToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setSenderId(message.getSender().getId());
        messageDTO.setSenderName(message.getSender().getFirstName() + " " + message.getSender().getLastName());
        messageDTO.setReceiverId(message.getReceiver().getId());
        messageDTO.setReceiverName(message.getReceiver().getFirstName() + " " + message.getReceiver().getLastName());
        messageDTO.setContent(message.getContent());
        messageDTO.setTimestamp(message.getTimestamp());
        messageDTO.setStatus(message.getStatus());
       // messageDTO.setRead(message.isRead());

        return messageDTO;
    }

    @Override
    public MessageDTO getMessageById(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
        return convertToDTO(message);
    }
    

    @Override
    public List<MessageDTO> getDeletedMessagesByStudent(Long studentId) {
        List<Message> messages = messageRepository.findByReceiverIdAndStatusOrderByTimestampDesc(studentId, "deleted");
        messages.addAll(messageRepository.findBySenderIdAndStatusOrderByTimestampDesc(studentId, "deleted"));
        return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public boolean deleteMessage(Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

//    @Override
//    public Long countUnreadMessages(Long studentId) {
//        return messageRepository.countByReceiverIdAndIsReadFalse(studentId);
//    }
    
}

