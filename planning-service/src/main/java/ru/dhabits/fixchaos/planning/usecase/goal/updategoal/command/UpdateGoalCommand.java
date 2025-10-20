package ru.dhabits.fixchaos.planning.usecase.goal.updategoal.command;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.dhabits.fixchaos.planning.enumeration.GoalType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateGoalCommand {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private GoalType goalType;
    private UUID directionId;
    private UUID parentId;
}
