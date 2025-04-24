package ru.dhabits.fixchaos.planning.usecase.goal.getsubtree.result;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.dhabits.fixchaos.planning.enumeration.GoalType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class GoalResultDto {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String mainDirection;
    private GoalType goalType;
    private List<GoalResultDto> goals;
}
