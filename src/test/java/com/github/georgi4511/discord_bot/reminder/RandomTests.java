package com.github.georgi4511.discord_bot.reminder;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;

public class RandomTests {
    private static final Logger log = LoggerFactory.getLogger(RandomTests.class);

    @Test
    void TestLocalTime() throws InterruptedException {
        var a= LocalTime.now();
        Thread.sleep(2_000);
        var b=LocalTime.now();
        Assert.isTrue(Duration.between(a,b).getSeconds()==2L,"Testing epoch seconds math");
    }
    @Test
    void TestLocalTimeString() throws InterruptedException {
        var a= LocalTime.now();
        Thread.sleep(2_000);
        var b=LocalTime.now();
        System.out.println(Duration.between(a,b).getSeconds());
    }

    @Test
    void TestSystemProperties(){
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    void test_splitVideoDest(){
        String line = "[download] Destination: C:\\Users\\Jerome\\IdeaProjects\\DiscordBot\\test.webm";
        String title;
        //= line.split("Destination: ")[1].split("\\.")[0]; // Get title without extension
        String[] split = line.split("Destination: ")[1].split("\\.")[0].split("\\\\");// Get title without extension
        title = split[split.length-1];
        String ext = line.split("Destination: ")[1].split("\\.")[1]; // Get extension

        log.info(title+" "+ext);
    }
}
