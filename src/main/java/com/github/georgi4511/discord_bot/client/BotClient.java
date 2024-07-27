package com.github.georgi4511.discord_bot.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.georgi4511.discord_bot.bot.Bot.createBot;

public class BotClient {

    public static Logger logger= LoggerFactory.getLogger(BotClient.class);

        public static void main (String[] args) throws Exception {
            createBot(args);
        }



}