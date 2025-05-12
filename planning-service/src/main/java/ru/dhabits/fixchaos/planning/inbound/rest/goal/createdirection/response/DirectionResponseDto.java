package ru.dhabits.fixchaos.planning.inbound.rest.goal.createdirection.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class DirectionResponseDto {
    private UUID id;
    private String code;
    private String name;
    private DirectionResponseDto parentDirection;
}
