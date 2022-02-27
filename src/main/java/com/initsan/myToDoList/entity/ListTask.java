package com.initsan.myToDoList.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "lists_tasks")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "rmv = 0")
public class ListTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @NotNull
    private Long listId;

    @Column
    @NotNull
    private Long taskId;

    public ListTask(Long listId, Long taskId) {
        this.listId = listId;
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListTask listTask)) return false;
        return listId.equals(listTask.listId) && taskId.equals(listTask.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listId, taskId);
    }

}
