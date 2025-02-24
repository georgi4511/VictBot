package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.model.GenerateImageInput;
import com.github.georgi4511.victbot.service.AiGenerationService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("ai")
public class AiController {
  private final AiGenerationService aiGenerationService;

  @GetMapping("/generate/joke")
  public Map<String, String> generate(
      @RequestParam(defaultValue = "Tell me a joke") String message) {
    return Map.of("generation", aiGenerationService.call(message));
  }

  @GetMapping("/generate/stream")
  public Flux<ChatResponse> generateJokeStream(
      @RequestParam(defaultValue = "Tell me a joke") String message) {
    Prompt prompt = new Prompt(new UserMessage(message));
    return aiGenerationService.stream(prompt);
  }

  @PostMapping("/generate/image")
  public ResponseEntity<byte[]> generateImage(@RequestBody GenerateImageInput input) {
    byte[] imageData = aiGenerationService.generateImage(input);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);

    return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
  }
}
