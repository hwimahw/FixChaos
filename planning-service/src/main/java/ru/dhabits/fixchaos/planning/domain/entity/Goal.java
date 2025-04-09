//package ru.dhabits.fixchaos.planning.domain.entity;
//
//import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
//import jakarta.persistence.*;
//import org.hibernate.annotations.Type;
//import ru.dhabits.fixchaos.planning.domain.entity.dictionary.MainDirection;
//import ru.dhabits.fixchaos.planning.enumeration.GoalType;
//
//import java.time.LocalDate;
//import java.util.UUID;
//
//public class Goal {
//    @Id
//    @GeneratedValue(generator = "UUID")
//    private UUID id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "start_date")
//    private LocalDate startDate;
//
//    @Column(name = "end_date")
//    private LocalDate endDate;
//
//    @Column(name = "goal_type")
//    @Enumerated(value = EnumType.STRING)
//    @Type(PostgreSQLEnumType.class)
//    private GoalType goalType;
//
//    @Column(name = "goal_id")
//    private Goal goal;
//
//    @ManyToOne
//    @JoinColumn(name = "main_direction_id")
//    private MainDirection mainDirection;
//}
