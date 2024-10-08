package com.github.georgi4511.discord_bot.reminder;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;

public class RandomTests {
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
}
