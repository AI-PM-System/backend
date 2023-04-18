package project.mainframe.api.openAI.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Choice entity.
 */
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Choice {

    /**
     * The id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    /**
     * The text.
     */
    @Column(length = 10000)
    private String text;

    /**
     * The index.
     * 
     * Note: index is a reserved word in SQL, so we use choice_index instead.
     */
    @Column(name = "choice_index")
    private int index;

    /**
     * The logprobs.
     */
    //private String logprobs;

    /**
     * The finish reason.
     */
    private String finish_reason;

    /**
     * No-args constructor
     */
    public Choice() {}
}
