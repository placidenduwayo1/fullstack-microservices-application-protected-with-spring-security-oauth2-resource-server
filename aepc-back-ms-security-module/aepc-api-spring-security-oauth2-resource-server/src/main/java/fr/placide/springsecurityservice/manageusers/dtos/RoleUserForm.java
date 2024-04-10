package fr.placide.springsecurityservice.manageusers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUserForm {
    private String username;
    private String role;
}
