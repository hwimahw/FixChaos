package ru.dhabits.fixchaos.planning.inbound.rest.direction.createdirection.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class CreateDirectionRequestDto {
    private String code;
    private String name;
    private String description;
    private UUID parentId;
}
