package com.initsan.myToDoList.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Status status;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime createDate;


}
