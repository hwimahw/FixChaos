package ru.dhabits.fixchaos.planning.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "LONG_TERM_GOALS", schema = "planning")
@Getter
@Setter
@Accessors(chain = true)
public class LongTermGoal {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "main_direction_id")
    private MainDirection mainDirection;
}
