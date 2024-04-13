package fr.placide.springsecurityservice.web.controller;

import fr.placide.springsecurityservice.exceptions.*;
import fr.placide.springsecurityservice.manageusers.dtos.RoleDto;
import fr.placide.springsecurityservice.manageusers.dtos.RoleUserForm;
import fr.placide.springsecurityservice.manageusers.dtos.UserDto;
import fr.placide.springsecurityservice.manageusers.entities.AppRole;
import fr.placide.springsecurityservice.manageusers.entities.AppUser;
import fr.placide.springsecurityservice.manageusers.service.AppService;
import fr.placide.springsecurityservice.securitymanager.jwtgenerator.DtoToken;
import fr.placide.springsecurityservice.securitymanager.jwtgenerator.JWTGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(value = "/api-auth")
@RequiredArgsConstructor
public class SecurityController {
    @Value("${message}")
    private String[] msg;
    private final AppService appService;
    private final JWTGeneratorService jwtService;

    @GetMapping(value = "")
    public Map<String, Object> getMsg() {
        return Map.of(
                msg[0], msg[1]
        );
    }

    @PostMapping(value = "/users")
    public AppUser createUser(@RequestBody UserDto dto) throws UserAlreadyExistsException, PasswordAndPasswordConfirmationNotMatchException {
        return appService.createUser(dto);
    }

    @PostMapping(value = "/roles")
    public AppRole createRole(@RequestBody RoleDto dto) throws RoleAlreadyExistsException {
        return appService.createRole(dto);
    }

    @GetMapping(value = "/users")
    Collection<AppUser> getAllUsers() {
        return appService.getAllUsers();
    }

    @GetMapping(value = "/roles")
    Collection<AppRole> getAllRoles() {
        return appService.getAllRoles();
    }

    @PostMapping(value = "/add-role-user")
    public AppUser addRoleToUser(@RequestBody RoleUserForm roleUserForm) throws
            AppRoleNotFoundException, AppUserNotFoundException, UserHasAlreadyThisRoleException {
        return appService.addRoleToUser(roleUserForm);
    }

    @PostMapping(value = "/remove-role-user")
    public AppUser removeRoleFromUser(@RequestBody RoleUserForm roleUserForm) throws AppRoleNotFoundException, AppUserNotFoundException {
        return appService.removeRoleFromUser(roleUserForm);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> generateToken(@RequestBody DtoToken dtoToken) {
        return jwtService.generateToken(dtoToken);
    }
    @DeleteMapping(value = "/users/id/{userId}")
    public void deleteUser(@PathVariable(name = "userId") Long userId) throws AppUserNotFoundException {
        appService.deleteUser(userId);
    }
    @GetMapping(value = "/users/id/{userId}")
    public AppUser getUserById(@PathVariable(name = "userId") Long userId) throws AppUserNotFoundException {
        return appService.getUserById(userId);
    }
}
