package fr.placide.springsecurityservice.manageusers.service;

import fr.placide.springsecurityservice.exceptions.*;
import fr.placide.springsecurityservice.manageusers.dtos.RoleDto;
import fr.placide.springsecurityservice.manageusers.dtos.RoleUserForm;
import fr.placide.springsecurityservice.manageusers.dtos.UserChgPwdDto;
import fr.placide.springsecurityservice.manageusers.dtos.UserDto;
import fr.placide.springsecurityservice.manageusers.entities.AppRole;
import fr.placide.springsecurityservice.manageusers.entities.AppUser;
import fr.placide.springsecurityservice.manageusers.mapper.AppRoleMapper;
import fr.placide.springsecurityservice.manageusers.mapper.AppUserMapper;
import fr.placide.springsecurityservice.manageusers.repositories.AppRoleRepository;
import fr.placide.springsecurityservice.manageusers.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
@Service
@RequiredArgsConstructor
@Slf4j
public class AppServiceImpl implements AppService{
    private final AppUserRepository userRepository;
    private final AppRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private String buildUsername(String firstname, String lastname){
        return firstname.toLowerCase().replace(" ","-")+
                "."+
                lastname.toLowerCase().replace(" ","-");
    }
    private String buildEmail(String username){
        return username+"@domain.fr";
    }
    @Override
    public AppUser createUser(UserDto dto) throws UserAlreadyExistsException {
        String username = buildUsername(dto.getFirstname().strip(), dto.getLastname().strip());
        String email = buildEmail(username);
        AppUser user = userRepository.findByUsername(username);
        if(user!=null){
            throw new UserAlreadyExistsException(
                    "User with login "+username+" already exists Exception");
        }

        dto.setFirstname(dto.getFirstname().strip());
        dto.setLastname(dto.getLastname().strip());
        dto.setPwd(passwordEncoder.encode(dto.getPwd()));
        AppUser appUser = AppUserMapper.from(dto);
        appUser.setUsername(username);
        appUser.setEmail(email);
        appUser.setCreateAt(Timestamp.from(Instant.now()).toString());
        return userRepository.save(appUser);
    }

    @Override
    public AppRole createRole(RoleDto dto) throws  RoleAlreadyExistsException {
        AppRole role = roleRepository.findByRole(dto.getRole());
        if(role!=null){
            throw new RoleAlreadyExistsException("Role already exists Exception");
        }
        dto.setRole(dto.getRole().strip().toUpperCase());
        AppRole appRole = AppRoleMapper.from(dto);
        appRole.setCreatedAt(Timestamp.from(Instant.now()).toString());
        return roleRepository.save(appRole);
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AppRole getRoleByRoleName(String roleName) {
        return roleRepository.findByRole(roleName);
    }

    @Override
    public Collection<AppRole> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Collection<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AppUser addRoleToUser(RoleUserForm roleUserForm) throws AppUserNotFoundException,
            AppRoleNotFoundException, UserHasAlreadyThisRoleException {
        AppUser user = userRepository.findByUsername(roleUserForm.getUsername());
        if(user==null){
            throw new AppUserNotFoundException("User not found Exception");
        }

        AppRole role = roleRepository.findByRole(roleUserForm.getRole());
        if(role==null){
            throw new AppRoleNotFoundException("Role not found Exception");
        }
        Collection<AppRole> roles = user.getRoles();
        if(roles.contains(role)) {
            throw new UserHasAlreadyThisRoleException(String.format("User %s  has already role %s Exception",roleUserForm.getUsername(),roleUserForm.getRole()));
        }
        roles.add(role);

        return userRepository.save(user);
    }

    @Override
    public AppUser removeRoleFromUser(RoleUserForm roleUserForm) throws AppUserNotFoundException, AppRoleNotFoundException {
        AppUser user = userRepository.findByUsername(roleUserForm.getUsername());
        if(user==null) throw new AppUserNotFoundException("User with that login "+roleUserForm.getUsername()+" not found Exception");
        AppRole role = roleRepository.findByRole(roleUserForm.getRole());
        if(role==null) throw new AppRoleNotFoundException("Role "+roleUserForm.getRole()+" not found Exception");
        user.getRoles().remove(role);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) throws AppUserNotFoundException {
        AppUser user = getUserById(userId);
        userRepository.delete(user);
    }

    @Override
    public AppUser getUserById(Long userId) throws AppUserNotFoundException {
        return userRepository.findById(userId).orElseThrow(()->new AppUserNotFoundException("User Not found Exception"));
    }

    @Override
    public AppUser changeUserPwd(UserChgPwdDto dto) throws AppUserNotFoundException,
             RegisteredPasswordNotMatchException, NewPasswordMuchSimilarToOldException {
        AppUser user = userRepository.findByUsername(dto.getUsername());
        log.error("password change payload {}",dto);
        if(user==null){
            throw new AppUserNotFoundException(String.format("User %s not found Exception", dto.getUsername()));
        }
        if(!passwordEncoder.matches(dto.getCurrentPwd(),user.getPwd())){
            throw new RegisteredPasswordNotMatchException("Registered Password not match with input password Exception");
        }
        if(passwordEncoder.matches(dto.getPwdNew(),user.getPwd())){
            throw new NewPasswordMuchSimilarToOldException("New Password is similar to Old One Exception");
        }
        user.setPwd(passwordEncoder.encode(dto.getPwdNew()));
       return userRepository.save(user);
    }
}
