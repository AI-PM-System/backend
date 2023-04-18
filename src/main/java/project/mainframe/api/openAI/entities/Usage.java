package project.mainframe.api.openAI.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Usage entity.
 */
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "openAI_usage")
public class Usage {
    
    /**
     * The id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * prompt tokens
     */
    private int prompt_tokens;

    /**
     * completion tokens
     */
    private int completion_tokens;

    /**
     * total tokens
     */
    private int total_tokens;

    /**
     * No-args constructor
     */
    public Usage() {}
}
