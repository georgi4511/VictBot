package com.github.georgi4511.victbot.services;

import com.github.georgi4511.victbot.models.DatabaseRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Data
@RequiredArgsConstructor
public class DatabaseCacheService {

    private DatabaseRepository databaseRepository;

    public Integer getGoodBotBotImpressions() {
        return databaseRepository.getBotImpressions().getGoodBotCount();
    }

    public Integer getBadBotBotImpressions() {
        return databaseRepository.getBotImpressions().getBadBodCount();
    }

    public void saveDatabase() throws IOException {
        databaseRepository.save();
    }

}
