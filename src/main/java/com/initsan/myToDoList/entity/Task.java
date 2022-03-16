package com.initsan.myToDoList.entity;

import com.initsan.myToDoList.dictionary.Status;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "rmv = 0")
@SQLDelete(sql = "UPDATE tasks SET rmv = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @NotNull
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime createDate;

    @Column
    @NotNull
    private int rmv;

    @Column(name = "user_id", nullable = false)
    @NotNull
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return status == task.status && title.equals(task.title)
                && Objects.equals(description, task.description)
                && Objects.equals(createDate, task.createDate)
                && Objects.equals(userId, task.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, title, description, createDate, userId);
    }
}
