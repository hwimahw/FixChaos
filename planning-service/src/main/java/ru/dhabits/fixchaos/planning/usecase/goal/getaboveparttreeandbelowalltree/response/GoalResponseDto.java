package ru.dhabits.fixchaos.planning.usecase.goal.getaboveparttreeandbelowalltree.response;

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
public class GoalResponseDto {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String direction;
    private GoalType goalType;
    private List<GoalResponseDto> goals;
}
