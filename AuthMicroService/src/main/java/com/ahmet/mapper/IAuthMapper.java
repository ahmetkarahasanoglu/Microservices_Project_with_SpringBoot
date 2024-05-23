package com.ahmet.mapper;

import com.ahmet.dto.request.DoLoginRequestDto;
import com.ahmet.dto.request.RegisterRequestDto;
import com.ahmet.dto.request.UserProfileSaveRequestDto;
import com.ahmet.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.repository.support.Repositories;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IAuthMapper {

    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);


    Auth toAuth(final RegisterRequestDto dto);
    @Mapping(target="authid", source="id") // source'daki "id" ile hedefteki "authid"yi eşleştirsin dedik.
    UserProfileSaveRequestDto fromAuth(final Auth auth);

}
