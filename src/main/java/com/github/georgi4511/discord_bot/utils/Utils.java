package com.github.georgi4511.discord_bot.utils;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import org.jetbrains.annotations.NotNull;

public class Utils {
    public static final String TWITTER_URL = "https://twitter.com/";
    public static final String X_URL = "https://x.com/";
    public static final String FXTWITTER_URL = "https://fxtwitter.com/";

    private Utils() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Cannot initialize helper class");
    }

    public static void fixTwitter(@NotNull MessageReceivedEvent event, @NotNull String content) {
        if(content.startsWith(X_URL)) {
            String fixedContent = String.format("%s sent this:%n%s", event.getAuthor().getEffectiveName(), content.replaceFirst(X_URL, FXTWITTER_URL));
            event.getMessage().delete().queue();
            event.getChannel().sendMessage(fixedContent).queue();
        }
        else if(content.startsWith(TWITTER_URL)) {
            String fixedContent = String.format("%s sent this:%n%s", event.getAuthor().getEffectiveName(), content.replaceFirst(TWITTER_URL, FXTWITTER_URL));
            event.getMessage().delete().queue();
            event.getChannel().sendMessage(fixedContent).queue();
        }
    }

}
