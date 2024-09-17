package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@Builder
@Getter
@Setter
@Entity
@Table(schema = SCHEME_AUTHORIZATION, name = "oauth2_scope")
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2Scope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

}