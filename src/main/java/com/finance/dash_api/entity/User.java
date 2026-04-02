package com.finance.dash_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active = true;

    private LocalDateTime creationDate = LocalDateTime.now();
}