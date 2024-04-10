package fr.placide.springsecurityservice.securitymanager.jwtgenerator;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DtoToken {
    private String username;
    private String pwd;
    private String grantType;
    private boolean withRefreshToken;
    private String refreshToken;
}
