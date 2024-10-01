package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "application", schema = "applications")
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('applications.application_id_application_seq')")
    @Column(name = "id_application", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "high_date", nullable = false)
    private Instant highDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "high_id_user", nullable = false)
    private UserEntity highIdUserEntity;

    @Column(name = "delete_date")
    private Instant deleteDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delete_id_user")
    private UserEntity deleteIdUserEntity;

}