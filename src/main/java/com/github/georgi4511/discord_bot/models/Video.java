package com.github.georgi4511.discord_bot.models;

public class Video {
    private String title;
    private String filePath;

    public Video(String title, String filePath) {
        this.title = title;
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public String getFilePath() {
        return filePath;
    }
}

