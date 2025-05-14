package example.chatapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages",
        indexes = {
                @Index(name = "idx_messages_chat_id", columnList = "chat_id"),
                @Index(name = "idx_messages_timestamp", columnList = "timestamp"),
                @Index(name = "idx_messages_sender_id", columnList = "sender_id"),
                @Index(name = "idx_unread_messages", columnList = "chat_id,read"
                        )
        })
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(nullable = false)
    private boolean read = false;

    // Custom methods to support message operations
    @PrePersist
    @PreUpdate
    public void updateChatActivity() {
        if (chat != null) {
            chat.updateLastActivity();
        }
    }
}
