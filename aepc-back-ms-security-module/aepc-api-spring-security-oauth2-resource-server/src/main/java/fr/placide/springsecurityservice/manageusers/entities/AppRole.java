package fr.placide.springsecurityservice.manageusers.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class AppRole {
    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(unique = true)
    private String role;
    private String createdAt;
}

