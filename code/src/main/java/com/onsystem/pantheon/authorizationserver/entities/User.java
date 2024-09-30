package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "\"user\"", schema = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_user_seq", allocationSize = 1)
    @Column(name = "id_user", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Size(max = 50)
    @NotNull
    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 30)
    @NotNull
    @Column(name = "login", nullable = false, length = 30)
    private String login;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "high_date", nullable = false)
    private Instant highDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "high_id_user")
    private User highIdUser;

    @Column(name = "delete_date")
    private Instant deleteDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delete_id_user")
    private User deleteIdUser;

}