package fr.placide.springsecurityservice.manageusers.mapper;

import fr.placide.springsecurityservice.manageusers.dtos.RoleDto;
import fr.placide.springsecurityservice.manageusers.entities.AppRole;
import org.springframework.beans.BeanUtils;

public class AppRoleMapper {
    private AppRoleMapper(){}
    public static AppRole from(RoleDto dto){
        AppRole appRole = new AppRole();
        BeanUtils.copyProperties(dto,appRole);

        return appRole;
    }
}
