package ru.dhabits.fixchaos.planning.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "CHANGE_THOUGHTS", schema = "planning")
@Getter
@Setter
@Accessors(chain = true)
public class ChangeThought {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "thought")
    private String thought;

    @Column(name = "date")
    private LocalDate date;

}
