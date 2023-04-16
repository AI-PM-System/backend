package project.mainframe.api.chat.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.entities.Artifact;
import project.mainframe.api.project.entities.Member;

/**
 * A chat is a conversation between two or more users.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Chat extends Artifact {
    
    /**
     * What is the name of the chat?
     */
    @Column(nullable = false)
    private String name;

    /**
     * A chat has one or more messages
     */
    @OneToMany
    private List<Message> messages;

    /**
     * A chat has one or more members
     */
    @OneToMany
    private List<Member> members;

    /**
     * When was the chat started?
     */
    @CreationTimestamp
    private LocalDateTime created;
    
    /**
     * No-args constructor
     */
    public Chat() {}
}
