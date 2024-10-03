package com.github.georgi4511.discord_bot.procedures;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Slf4j
@NoArgsConstructor
@Service
@Getter
public class LoggingProcedure {

   private static final long startTime = Instant.now().getEpochSecond();

    @Scheduled(fixedRateString = "${procedure.rate}")
    @Async
    public void log() {
        log.info("Bot has been alive for {} seconds", Instant.now().getEpochSecond() - startTime);
    }
}
