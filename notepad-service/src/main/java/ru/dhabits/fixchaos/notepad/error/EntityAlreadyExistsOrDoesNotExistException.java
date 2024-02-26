package ru.dhabits.fixchaos.notepad.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityAlreadyExistsOrDoesNotExistException extends RuntimeException {
    public EntityAlreadyExistsOrDoesNotExistException(String message) {
        super(message);
    }
}
