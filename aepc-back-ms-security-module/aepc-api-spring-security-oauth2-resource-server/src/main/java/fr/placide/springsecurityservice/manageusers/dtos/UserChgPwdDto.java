package fr.placide.springsecurityservice.manageusers.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserChgPwdDto {
    private String username;
    private String currentPwd;
    private String pwdNew;
}
