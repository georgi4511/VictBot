/* (C)2025 */
package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.service.SpringAI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("ai")
public class ChatController {

  //    private final OllamaChatModel chatModel;
  private final SpringAI springAiService;

  @GetMapping("/")
  public Map<String, String> generate() {
    return Map.of("Hello", "World");
  }

  @GetMapping("/generate")
  public Map<String, String> generate(
      @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
    return Map.of("generation", springAiService.call(message));
  }

  @GetMapping("/generateStream")
  public Flux<ChatResponse> generateStream(
      @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
    Prompt prompt = new Prompt(new UserMessage(message));
    return springAiService.stream(prompt);
  }

  @GetMapping("/amogus")
  public Flux<String> generateAmogus() {
    return springAiService.getAmongus();
  }
}
