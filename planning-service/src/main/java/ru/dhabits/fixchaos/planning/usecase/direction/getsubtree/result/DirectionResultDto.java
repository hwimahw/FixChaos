package ru.dhabits.fixchaos.planning.usecase.direction.getsubtree.result;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class DirectionResultDto {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private List<DirectionResultDto> directions;
}
