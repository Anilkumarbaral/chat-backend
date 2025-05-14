package example.chatapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats",
        indexes = {
                @Index(name = "idx_chats_last_activity", columnList = "last_activity")
        })
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(name = "is_group_chat", nullable = false)
    private boolean isGroupChat = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chat_participants",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            indexes = {
                    @Index(name = "idx_chat_participants_user_id", columnList = "user_id")
            }
    )
    private List<User> participants = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("timestamp DESC")
    private List<Message> messages = new ArrayList<>();

    // Helper methods for chat management
    public void addParticipant(User user) {
        this.participants.add(user);
        user.getChats().add(this);
    }

    public void removeParticipant(User user) {
        this.participants.remove(user);
        user.getChats().remove(this);
    }

    public void updateLastActivity() {
        this.lastActivity = LocalDateTime.now();
    }
}