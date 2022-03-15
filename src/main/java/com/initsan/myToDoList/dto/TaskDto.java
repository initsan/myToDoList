package com.initsan.myToDoList.dto;

import com.initsan.myToDoList.dictionary.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Задача")
public class TaskDto {

    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Статус задачи")
    private Status status;

    @Schema(description = "Заголовок задачи")
    private String title;

    @Schema(description = "Описание задачи")
    private String description;

    @Schema(description = "Дата создания")
    private LocalDateTime createDate;

    @Schema(description = "Идентификатор автора")
    private Long userId;

}
