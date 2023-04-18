package project.mainframe.api.openAI.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Completion entity.
 */
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Completion {
    
    /**
     * The id.
     */
    @Id
    private String id;

    /**
     * The object.
     */
    private String object;

    /**
     * When the object was created.
     */
    private int created;

    /**
     * The model.
     */
    private String model;

    /**
     * Has many choices.
     */
    @OneToMany(fetch = FetchType.EAGER)
    private List<Choice> choices;

    /**
     * Has one usage.
     */
    @OneToOne
    private Usage usage;

    /**
     * The creation date
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * No-args constructor
     */
    public Completion() {}
}
