package project.mainframe.api.project.generator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.entities.User;

/**
 * A project generator message is a message in a project generator.
 */
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project_generator_message")
public class GeneratorMessage {
    
    /**
     * The id of the project generator
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The content of the message
     */
    @Column(nullable = false, length = 1000)
    private String content;

    /**
     * Belongs to one project generator
     */
    @ManyToOne
    private Generator projectGenerator;

    /**
     * A message belongs to one user
     */
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    /**
     * No-args constructor.
     */
    public GeneratorMessage() {}

    /**
     * Constructor with content and project generator.
     * 
     * @param content The content of the message
     * @param projectGenerator The project generator
     */
    public GeneratorMessage(String content, Generator projectGenerator, User user) {
        this.content = content;
        this.projectGenerator = projectGenerator;
        this.user = user;
    }
}
