package com.github.georgi4511.victbot.procedures;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

@NoArgsConstructor
@Service
public class LoggingProcedure {

   private static final LocalTime startTime = LocalTime.now();
    private static final Logger log = LoggerFactory.getLogger(LoggingProcedure.class);

    @Scheduled(fixedRateString = "${procedure.rate}")
    @Async
    public void logging() {
        log.info("Bot has been alive for {} seconds", Duration.between(startTime,LocalTime.now()).getSeconds());
    }
}
