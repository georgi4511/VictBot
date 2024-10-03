package com.github.georgi4511.discord_bot.scripts.clean_commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        cleanCommands(args[0]);
    }

    private static void cleanCommands(String token) throws InterruptedException {
        JDA jda = JDABuilder.createDefault(token).build();
        jda.updateCommands().queue();
        Thread.sleep(5_000);
        jda.shutdown();
    }

}
