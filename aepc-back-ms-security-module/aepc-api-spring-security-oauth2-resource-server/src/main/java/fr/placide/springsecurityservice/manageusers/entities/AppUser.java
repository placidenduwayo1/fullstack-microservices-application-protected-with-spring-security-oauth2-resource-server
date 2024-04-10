package fr.placide.springsecurityservice.manageusers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long userId;
    @Column(unique = true)
    private String username;
    private String pwd;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String createAt;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<AppRole> roles = new ArrayList<>();
}


