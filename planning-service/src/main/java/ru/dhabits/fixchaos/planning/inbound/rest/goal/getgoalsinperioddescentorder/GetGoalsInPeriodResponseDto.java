package ru.dhabits.fixchaos.planning.inbound.rest.goal.getgoalsinperioddescentorder;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GetGoalsInPeriodResponseDto {

    private UUID id;
    private String name;
    private String startDate;
    private String endDate;
    private String direction;
    private String goalType;
    private List<GetGoalsInPeriodResponseDto> goals;
}
