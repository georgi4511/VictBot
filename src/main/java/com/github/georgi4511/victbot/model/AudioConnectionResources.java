package com.github.georgi4511.victbot.model;

import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public record AudioConnectionResources(
        List<Byte> connectionAudioResources, AudioManager connectionPlayer, Integer connectionTimeout) {}
