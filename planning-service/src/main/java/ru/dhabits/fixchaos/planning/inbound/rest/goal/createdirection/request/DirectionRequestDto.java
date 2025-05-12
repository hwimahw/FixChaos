package ru.dhabits.fixchaos.planning.inbound.rest.goal.createdirection.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class DirectionRequestDto {
    private String code;
    private String name;
    private UUID parentId;
}
