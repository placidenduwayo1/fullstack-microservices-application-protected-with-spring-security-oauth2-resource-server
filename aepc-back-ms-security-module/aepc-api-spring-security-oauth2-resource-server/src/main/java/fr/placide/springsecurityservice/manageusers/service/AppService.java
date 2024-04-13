package fr.placide.springsecurityservice.manageusers.service;

import fr.placide.springsecurityservice.exceptions.*;
import fr.placide.springsecurityservice.manageusers.dtos.RoleDto;
import fr.placide.springsecurityservice.manageusers.dtos.RoleUserForm;
import fr.placide.springsecurityservice.manageusers.dtos.UserDto;
import fr.placide.springsecurityservice.manageusers.entities.AppRole;
import fr.placide.springsecurityservice.manageusers.entities.AppUser;

import java.util.Collection;

public interface AppService {
    AppUser createUser(UserDto dto) throws UserAlreadyExistsException, PasswordAndPasswordConfirmationNotMatchException;
    AppRole createRole(RoleDto dto) throws  RoleAlreadyExistsException;
    AppUser getUserByUsername(String username);
    AppRole getRoleByRoleName(String roleName);
    Collection<AppRole> getAllRoles();
    Collection<AppUser> getAllUsers();
    AppUser addRoleToUser(RoleUserForm roleUserForm) throws AppUserNotFoundException, AppRoleNotFoundException, UserHasAlreadyThisRoleException;
    AppUser removeRoleFromUser(RoleUserForm roleUserForm) throws AppUserNotFoundException, AppRoleNotFoundException;
    void deleteUser(Long userId) throws AppUserNotFoundException;

    AppUser getUserById(Long userId) throws AppUserNotFoundException;
}
