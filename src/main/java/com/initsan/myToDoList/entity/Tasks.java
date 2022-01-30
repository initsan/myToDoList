package com.initsan.myToDoList.entity;

import com.initsan.myToDoList.Status;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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
//@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tasks tasks)) return false;
        return status == tasks.status && title.equals(tasks.title) && Objects.equals(description, tasks.description) && Objects.equals(createDate, tasks.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, title, description, createDate);
    }
}
