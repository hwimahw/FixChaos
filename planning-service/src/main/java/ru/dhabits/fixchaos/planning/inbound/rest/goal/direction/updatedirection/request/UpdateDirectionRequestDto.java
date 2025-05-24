package ru.dhabits.fixchaos.planning.inbound.rest.goal.direction.updatedirection.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateDirectionRequestDto {
    private String code;
    private String name;
    private String description;
    private UUID parentId;
}
