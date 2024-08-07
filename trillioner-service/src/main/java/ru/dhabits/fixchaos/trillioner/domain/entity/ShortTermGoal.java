package ru.dhabits.fixchaos.trillioner.domain.entity;

import jakarta.persistence.CascadeType;
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
import ru.dhabits.fixchaos.trillioner.domain.entity.dictionary.MainDirection;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "SHORT_TERM_GOALS", schema = "trillioner")
@Getter
@Setter
@Accessors(chain = true)
public class ShortTermGoal {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne()
    @JoinColumn(name = "main_direction_id")
    private MainDirection mainDirection;
}
