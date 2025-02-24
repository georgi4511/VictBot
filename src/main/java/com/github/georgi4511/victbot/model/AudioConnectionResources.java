package com.github.georgi4511.victbot.model;

import lombok.Builder;
import lombok.Getter;
import net.dv8tion.jda.api.managers.AudioManager;

@Builder
@Getter
public class AudioConnectionResources {
  Byte[] connectionAudioResources;
  AudioManager connectionPlayer;
  Integer connectionTimeout;
}
