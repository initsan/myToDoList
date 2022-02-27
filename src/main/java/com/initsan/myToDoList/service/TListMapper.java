package com.initsan.myToDoList.service;

import com.initsan.myToDoList.dto.TListDto;
import com.initsan.myToDoList.entity.TList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TListMapper {

    TListDto tListToTListDto(TList tList);
    TList tListDtoToTList(TListDto tListDto);

}
