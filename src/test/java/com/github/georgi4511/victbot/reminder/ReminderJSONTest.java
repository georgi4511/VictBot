package com.github.georgi4511.victbot.reminder;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

class ReminderJSONTest {

    @Test
            void testReminder() throws IOException {
            Path basedir = Path.of(".");
            if (!basedir.toFile().isDirectory()) throw new IOException();
            boolean directoryExists = Arrays.stream(Objects.requireNonNull(basedir.toFile().listFiles())).anyMatch(f->f.isDirectory()&& f.getName().equals("jsons"));

            assert directoryExists;
    }
}