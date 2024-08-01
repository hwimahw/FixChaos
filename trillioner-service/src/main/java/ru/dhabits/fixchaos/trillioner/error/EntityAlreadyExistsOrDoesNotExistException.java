package ru.dhabits.fixchaos.trillioner.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityAlreadyExistsOrDoesNotExistException extends RuntimeException {
    public EntityAlreadyExistsOrDoesNotExistException(String message) {
        super(message);
    }
}
