package com.github.georgi4511.victbot.services;

import com.github.georgi4511.victbot.models.DatabaseRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Data
public class DatabaseCacheService {

    private DatabaseRepository databaseRepository;

    public DatabaseCacheService() {
        databaseRepository = new DatabaseRepository();
    }

    public Integer getGoodBotImpressions() {
        return databaseRepository.getDatabase().getBotImpressions().getGoodBotCount();
    }

    public void setGoodBotImpressions(Integer input) {
        databaseRepository.getDatabase().getBotImpressions().setGoodBotCount(input);
    }

    public Integer getBadBotImpressions() {
        return databaseRepository.getDatabase().getBotImpressions().getBadBodCount();
    }

    public void setBadBotImpressions(Integer input) {
        databaseRepository.getDatabase().getBotImpressions().setBadBodCount(input);
    }

    public void saveDatabase() throws IOException {
        databaseRepository.save();
    }

}
