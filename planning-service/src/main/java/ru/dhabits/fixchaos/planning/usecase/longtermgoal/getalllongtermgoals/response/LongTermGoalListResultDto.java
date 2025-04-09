package ru.dhabits.fixchaos.planning.usecase.longtermgoal.getalllongtermgoals.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class LongTermGoalListResultDto {
    private List<LongTermGoalInfo> content;
    private Long totalElements;
    private Long number;
    private Long totalPages;
    private Long numberOfElements;
}
