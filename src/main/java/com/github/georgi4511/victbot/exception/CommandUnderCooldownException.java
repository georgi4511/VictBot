package com.github.georgi4511.victbot.exception;

public class CommandUnderCooldownException extends RuntimeException {
    public CommandUnderCooldownException(String message) {
        super(message);
    }
}
