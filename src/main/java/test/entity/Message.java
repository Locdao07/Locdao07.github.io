package test.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Student sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Student receiver;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    @Column(name = "status", nullable = false)
    private String status;
//    @Column(name = "is_read", nullable = false)
//    private boolean isRead = false; // Field to track if the message is read
    @Column(name = "is_deleted", nullable = false)
    private boolean deleted; // Flag to mark message as deleted
    
    // Getters and Setters
//    @Column(name = "sender_deleted", nullable = false)
//    private boolean senderDeleted = false;
//
//    @Column(name = "receiver_deleted", nullable = false)
//    private boolean receiverDeleted = false;
    
}


