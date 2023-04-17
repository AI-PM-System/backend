package project.mainframe.api.chat.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.chat.enums.ChatType;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.entities.Project;

/**
 * A chat is a conversation between two or more users.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Chat {

    /**
     * The id of the chat
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * What is the name of the chat?
     */
    @Column(nullable = false)
    private String name;

    /**
     * What type of chat is this?
     */
    @Column(nullable = false)
    private ChatType type;

    /**
     * A chat belongs to one project
     */
    @OneToOne
    private Project project;

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
