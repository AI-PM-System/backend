package project.mainframe.api.task.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A board list is a collection of tasks. It can be used to manage tasks in a project.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class BoardList {
    
    /**
     * The id of the board list.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The name of the board list.
     */
    private String name;

    /**
     * The board list belongs to one board.
     */
    @ManyToOne
    private Board board;

    /**
     * The board has one or more tasks.
     */
    @OneToMany(fetch = FetchType.EAGER)
    private List<Task> tasks;

    /**
     * No-args constructor.
     */
    public BoardList() {}
}
