package com.github.georgi4511.discord_bot.procedures;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoggingProcedure {

 @Scheduled(fixedRateString = "${procedure.rate}")
    @Async
    public void log() {
        log.info("Logging procedure is running");
    }



}
