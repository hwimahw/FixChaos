package ru.dhabits.fixchaos.planning.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.Instrument;
import ru.dhabits.fixchaos.planning.enumeration.GoalType;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "GOALS", schema = "planning")
@Getter
@Setter
@Accessors(chain = true)
public class Goal {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "goal_type")
    @Enumerated(value = EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private GoalType goalType;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "goal")
    private List<Goal> goals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "goal")
    private List<Instrument> instruments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return Objects.equals(id, goal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
