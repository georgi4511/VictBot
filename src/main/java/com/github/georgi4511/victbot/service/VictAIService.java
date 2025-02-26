/* (C)2025 */
package com.github.georgi4511.victbot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class VictAIService {
  private final ChatClient chatClient;

  public VictAIService(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  public Flux<String> getAmongus() {
    return chatClient.prompt().user("Amogus").stream().content();
  }

  public String call(String message) {
    return chatClient.prompt().user(message).call().content();
  }

  public Flux<ChatResponse> stream(Prompt prompt) {
    return chatClient.prompt(prompt).stream().chatResponse();
  }
}
