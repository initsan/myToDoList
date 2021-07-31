package com.initsan.myToDoList.entity;

import com.initsan.myToDoList.Status;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@Builder
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //TODO set default value to column
    @Column(columnDefinition = "enum('Processing', 'Done') default 'Unknown'")
    private Status status;

    @Column
    private String title;

    @Column
    private String description;

    //TODO set default value to column
    @Column
    private LocalDateTime createDate;


}
