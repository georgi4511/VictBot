package com.github.georgi4511.victbot.util;

import javax.validation.constraints.NotNull;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Util {
  public static final String TWITTER_URL = "https://twitter.com/";
  public static final String X_URL = "https://x.com/";
  public static final String VXTWITTER_URL = "https://vxtwitter.com/";

  private Util() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Cannot initialize helper class");
  }

  public static void fixTwitter(@NotNull MessageReceivedEvent event, @NotNull String content) {
    boolean startsWithX = content.startsWith(X_URL);
    boolean startsWithTwitter = content.startsWith(TWITTER_URL);

    if (startsWithX || startsWithTwitter) {
      String fixedContent =
          String.format(
              "%s sent this:%n%s",
              event.getAuthor().getEffectiveName(),
              content.replaceFirst(startsWithX ? X_URL : TWITTER_URL, VXTWITTER_URL));

      event.getMessage().delete().queue();
      event.getChannel().sendMessage(fixedContent).queue();
    }
  }
}
