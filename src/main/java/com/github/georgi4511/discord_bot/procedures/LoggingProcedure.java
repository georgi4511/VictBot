package com.github.georgi4511.discord_bot.procedures;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

@Slf4j
@Service
public class LoggingProcedure {

    private static final LocalTime startTime = LocalTime.now();



    @Scheduled(fixedRateString = "${procedure.rate}")
    @Async
    public void log() {
        log.info("Bot has been alive for {} seconds", Duration.between(startTime,LocalTime.now()).getSeconds());
    }


}
