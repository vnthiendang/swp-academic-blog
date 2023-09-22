package com.swp.cms.mapper;

import com.swp.cms.dto.UserDto;
import com.swp.entities.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @IterableMapping(elementTargetType = UserDto.class, qualifiedByName = "fromEntityToUserDto")
    @Named(value = "fromEntityToUserDtoList")
    public List<UserDto> fromEntityToUserDtoList(List<User> input);

    @Named(value = "fromEntityToUserDto")
    public UserDto fromEntityToUserDto(User input);
}
