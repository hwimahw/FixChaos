package ru.dhabits.fixchaos.planning.inbound.rest.goal.updategoal.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.dhabits.fixchaos.planning.enumeration.GoalType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateGoalRequestDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private GoalType goalType;
    private UUID directionId;
    private UUID parentId;
}
