package com.github.georgi4511.discord_bot.utils;

import lombok.Value;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class Utils {
    public static final String TWITTER_URL = "https://twitter.com/";
    public static final String X_URL = "https://x.com/";
    public static final String FXTWITTER_URL = "https://fxtwitter.com";

    private Utils(){
        //noop
    }

    public static void fixTwitter(@NotNull MessageReceivedEvent event, @NotNull String content) {
        String urlKey = X_URL;
        if(content.startsWith(TWITTER_URL)) {
            urlKey = TWITTER_URL;
        }
        event.getMessage().delete().queue();
        String fixedContent = String.format("%s sent this:%n%s",event.getAuthor().getEffectiveName() , content.replaceFirst(urlKey, FXTWITTER_URL));
        event.getChannel().sendMessage(fixedContent).queue();
    }

}
