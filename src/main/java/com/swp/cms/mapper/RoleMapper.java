package com.swp.cms.mapper;

import com.swp.cms.dto.UserRoleDto;
import com.swp.cms.dto.UserRoleDto;
import com.swp.entities.Role;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @IterableMapping(qualifiedByName = "fromEntityToRoleDto")
    @Named(value = "fromEntityToRoleDtoList")
    public List<UserRoleDto> fromEntityToRoleDtoList(List<Role> input);

    @Named(value = "fromEntityToRoleDto")
    public UserRoleDto fromEntityToRoleDto(Role input);
}