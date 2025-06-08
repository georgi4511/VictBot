package com.github.georgi4511.victbot.procedures;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Profile({"local", "prod"})
@Service
public class LoggingProcedure {

  private static final Logger log = LoggerFactory.getLogger(LoggingProcedure.class);
  private long timeAlive = 0L;

  @Scheduled(cron = "${vict.procedure.logging.cron}")
  @Async
  public void logging() {
    timeAlive++;
    log.info("Bot has been alive for {} hours", timeAlive);
  }
}
