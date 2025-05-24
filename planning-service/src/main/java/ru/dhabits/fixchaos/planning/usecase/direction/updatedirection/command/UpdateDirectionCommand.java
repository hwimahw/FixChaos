package ru.dhabits.fixchaos.planning.usecase.direction.updatedirection.command;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateDirectionCommand {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private UUID parentId;
}
