package test.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageDTO {
	 private Long id;
	    private Long senderId;
	    private String senderName;
	    private Long receiverId;
	    private String receiverName;
	    private String receiverEmail;
	    @NotBlank(message = "Nội dung tin nhắn không được để trống")
	    private String content;
	    private LocalDateTime timestamp;
	    private String status;
	    private List<String> receiverNames;
	    private String receiverNamesFormatted;
	    private String shortContent;
	   // private boolean isRead; 
	    
	    public String formatRecipientNames() {
	        if (receiverNames == null || receiverNames.isEmpty()) {
	            return "";
	        }

	        StringBuilder formattedNames = new StringBuilder();
	        int size = receiverNames.size();
	        
	        if (size > 2) {
	            formattedNames.append(receiverNames.get(0)).append(", ")
	                          .append(receiverNames.get(1)).append(", [").append(size - 2).append("...]");
	        } else {
	            for (int i = 0; i < size; i++) {
	                if (i > 0) {
	                    formattedNames.append(", ");
	                }
	                formattedNames.append(receiverNames.get(i));
	            }
	        }
	        
	        return formattedNames.toString();
	    }
    // Getters and Setters
}

