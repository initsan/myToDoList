package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.TListDto;
import com.initsan.myToDoList.entity.TList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TListMapper {

    TListDto tListToTListDto(TList tList);

    @Mapping(target = "rmv", defaultValue = "0", ignore = true)
    TList tListDtoToTList(TListDto tListDto);

}
