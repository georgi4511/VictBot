package com.github.georgi4511.victbot.procedures;

import com.github.georgi4511.victbot.services.DatabaseCacheService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DatabaseProcedure {

    private static final LocalTime startTime = LocalTime.now();
    private static final Logger log = LoggerFactory.getLogger(DatabaseProcedure.class);
    private DatabaseCacheService databaseCacheService;


    @Scheduled(fixedRateString = "${procedure.rate}")
    @Async
    public void saveDatabaseCache() {
        try {
            databaseCacheService.saveDatabase();
        } catch (IOException e) {
            log.error("Failed to save database cache {}", e.getMessage(), e);
        }
    }
}
