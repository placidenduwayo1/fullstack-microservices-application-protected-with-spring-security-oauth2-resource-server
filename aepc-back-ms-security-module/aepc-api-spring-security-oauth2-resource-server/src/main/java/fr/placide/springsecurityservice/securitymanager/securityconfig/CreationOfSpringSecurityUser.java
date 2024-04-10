package fr.placide.springsecurityservice.securitymanager.securityconfig;

import fr.placide.springsecurityservice.manageusers.entities.AppUser;
import fr.placide.springsecurityservice.manageusers.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreationOfSpringSecurityUser implements UserDetailsService {
    private final AppUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles()
                .forEach(
                        role-> authorities.add(new SimpleGrantedAuthority(role.getRole()))
                );
        return new User(user.getUsername(), user.getPwd(),authorities);
    }
}
