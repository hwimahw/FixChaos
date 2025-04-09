package ru.dhabits.fixchaos.planning.usecase.shorttermgoal.updateshorttermgoal.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateShortTermGoalCommand {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String mainDirection;
}
