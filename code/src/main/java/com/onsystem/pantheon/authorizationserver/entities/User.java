package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_USER;

@Getter
@Setter
@Entity
@Table(schema = SCHEME_USER, name = "\"user\"", uniqueConstraints = {
        @UniqueConstraint(name = "user_email_key", columnNames = {"email"}),
        @UniqueConstraint(name = "user_login_key", columnNames = {"login"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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