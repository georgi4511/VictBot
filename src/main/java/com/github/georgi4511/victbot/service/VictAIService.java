package com.github.georgi4511.victbot.service;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "vict.stable-diffusion.active", havingValue = "true")
public class VictAIService {
  private final OllamaApi ollamaApi;

  public VictAIService(OllamaApi.Builder builder) {
    this.ollamaApi = builder.build();
  }

  public String call(String message) {
    return ollamaApi.chat(OllamaApi.ChatRequest.builder(message).build()).message().content();
  }
}
