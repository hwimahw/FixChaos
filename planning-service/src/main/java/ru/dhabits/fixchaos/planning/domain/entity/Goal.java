package ru.dhabits.fixchaos.planning.domain.entity;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
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
import org.hibernate.annotations.Type;
import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;
import ru.dhabits.fixchaos.planning.enumeration.GoalType;

import java.time.LocalDate;
import java.util.List;
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
    @Type(PostgreSQLEnumType.class)
    private GoalType goalType;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "goal")
    private List<Goal> goals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "main_direction_id")
    private MainDirection mainDirection;
}
