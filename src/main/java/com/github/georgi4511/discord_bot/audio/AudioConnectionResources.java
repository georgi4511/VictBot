package com.github.georgi4511.discord_bot.audio;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.managers.AudioManager;

@Builder
@Getter
@NonNull
public class AudioConnectionResources {
  Byte[] connectionAudioResources;
   AudioManager connectionPlayer;
   Integer connectionTimeout;
}
