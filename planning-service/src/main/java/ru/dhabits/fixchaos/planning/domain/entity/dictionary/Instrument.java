package ru.dhabits.fixchaos.planning.domain.entity.dictionary;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Immutable;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;

import java.util.UUID;

@Entity
@Table(name = "DIRECTIONS", schema = "planning")
@Getter
@Setter
@Immutable
@Accessors(chain = true)
public class Instrument {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;
}
