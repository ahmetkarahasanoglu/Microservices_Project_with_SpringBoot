package com.ahmet.mapper;

import com.ahmet.dto.request.AddUserRequestDto;
import com.ahmet.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IUserProfileMapper {

    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);

    @Mapping(source = "id", target = "userprofileid")
    UserProfile toUserProfile(final AddUserRequestDto dto);
}
