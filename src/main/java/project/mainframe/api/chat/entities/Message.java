package project.mainframe.api.chat.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.entities.Member;

/**
 * A message is a single message in a chat.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Message {

    /**
     * The id of the message
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A message belongs to one chat
     */
    @OneToOne
    private Chat chat;

    /**
     * A message belongs to one member
     */
    @OneToOne
    private Member member;

    /**
     * What is the content of the message?
     */
    @Column(nullable = false)
    private String content;

    /**
     * When was the message sent?
     */
    @CreationTimestamp
    private LocalDateTime sent;

    /**
     * No-args constructor
     */
    public Message() {}
    
}
