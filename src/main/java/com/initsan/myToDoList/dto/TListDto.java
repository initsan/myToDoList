package com.initsan.myToDoList.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Список задач")
public class TListDto {

    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Название")
    private String name;

    @Schema(description = "Идентификатор автора")
    private Long userId;

}
