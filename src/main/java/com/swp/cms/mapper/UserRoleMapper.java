package com.swp.cms.mapper;

import com.swp.cms.dto.UserRoleDto;
import com.swp.entities.Role;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    @IterableMapping(elementTargetType = UserRoleDto.class, qualifiedByName = "fromEntityToUserRoleDto")
    @Named(value = "fromEntityToUserRoleDtoList")
    public List<UserRoleDto> fromEntityToUserRoleDtoList(List<Role> input);

    @Named(value = "fromEntityToUserRoleDto")
    public UserRoleDto fromEntityToUserRoleDto(Role input);
}
