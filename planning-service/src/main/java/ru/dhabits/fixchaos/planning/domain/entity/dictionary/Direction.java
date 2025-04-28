package ru.dhabits.fixchaos.planning.domain.entity.dictionary;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Immutable;
import ru.dhabits.fixchaos.planning.domain.entity.Goal;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "DIRECTIONS", schema = "planning")
@Getter
@Setter
@Immutable
@Accessors(chain = true)
public class Direction {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String code;
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "direction")
    private List<Direction> directions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Direction direction;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "direction")
    private List<Goal> goals;
}
