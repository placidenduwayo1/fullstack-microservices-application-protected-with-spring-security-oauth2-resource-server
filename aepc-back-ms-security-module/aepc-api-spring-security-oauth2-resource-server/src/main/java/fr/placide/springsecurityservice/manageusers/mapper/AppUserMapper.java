package fr.placide.springsecurityservice.manageusers.mapper;

import fr.placide.springsecurityservice.manageusers.dtos.UserDto;
import fr.placide.springsecurityservice.manageusers.entities.AppUser;
import org.springframework.beans.BeanUtils;

public class AppUserMapper {

    private AppUserMapper(){}
    public static AppUser from(UserDto dto) {
        AppUser bean = new AppUser();
        BeanUtils.copyProperties(dto, bean);

        return bean;
    }
}
