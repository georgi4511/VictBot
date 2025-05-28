package com.github.georgi4511.victbot.model;

import java.util.List;
import net.dv8tion.jda.api.managers.AudioManager;

public record AudioConnectionResources(
    List<Byte> connectionAudioResources,
    AudioManager connectionPlayer,
    Integer connectionTimeout) {}
