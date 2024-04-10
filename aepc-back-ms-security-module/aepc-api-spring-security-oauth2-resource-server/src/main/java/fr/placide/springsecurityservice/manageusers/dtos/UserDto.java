package fr.placide.springsecurityservice.manageusers.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String firstname;
    private String lastname;
    private String pwd;
    private String pwdConfirm;
}
