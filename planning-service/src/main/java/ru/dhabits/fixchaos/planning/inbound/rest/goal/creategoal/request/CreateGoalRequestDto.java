package ru.dhabits.fixchaos.planning.inbound.rest.goal.creategoal.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class CreateGoalRequestDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String goalType;
    private UUID directionId;
    private UUID parentId;
}
