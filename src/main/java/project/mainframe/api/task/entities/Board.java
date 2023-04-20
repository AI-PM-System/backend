package project.mainframe.api.task.entities;

import java.util.List;

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
import project.mainframe.api.project.entities.Project;

/**
 * A board is a collection of lists. It can be used to manage tasks in a project.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Board {
    
    /**
     * The id of the board.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The name of the board.
     */
    private String name;

    /**
     * The description of the board.
     */
    private String description;

    /**
     * The board belongs to one project.
     */
    @OneToOne
    private Project project;

    /**
     * The board has one or more lists.
     */
    @OneToMany
    private List<BoardList> lists;

    /**
     * No-args constructor.
     */
    public Board() {}
}
