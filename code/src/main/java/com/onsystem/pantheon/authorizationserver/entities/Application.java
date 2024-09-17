package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_APPLICATIONS;

@Getter
@Setter
@Entity
@Table(schema = SCHEME_APPLICATIONS, name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_application", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @CreatedDate
    @NotNull
    @Column(name = "high_date", nullable = false)
    private Instant highDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "high_id_user", nullable = false)
    private User highIdUser;

    @Column(name = "delete_date")
    private Instant deleteDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delete_id_user")
    private User deleteIdUser;

}