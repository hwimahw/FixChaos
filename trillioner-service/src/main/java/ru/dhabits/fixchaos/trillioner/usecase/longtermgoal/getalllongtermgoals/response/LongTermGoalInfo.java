package ru.dhabits.fixchaos.trillioner.usecase.longtermgoal.getalllongtermgoals.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class LongTermGoalInfo {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String mainDirection;
}
