package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.exception.GenerateImageException;
import com.github.georgi4511.victbot.model.GenerateImageInput;
import com.github.georgi4511.victbot.model.GenerateImageResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.Base64;

@Service
public class AiGenerationService {
  private static final String GENERATE_ENDPOINT = "/sdapi/v1/txt2img";
    private final ChatClient chatClient;
  private final RestTemplate restTemplate;

  public AiGenerationService(
      @Value("${vict.stable-diffusion.url}") String stableDiffusionUrl,
      ChatClient.Builder builder,
      RestTemplateBuilder restTemplateBuilder) {
    this.chatClient = builder.build();
    this.restTemplate = restTemplateBuilder.rootUri(stableDiffusionUrl).build();
  }

  public String call(String message) {
    return chatClient.prompt().user(message).call().content();
  }

  public Flux<ChatResponse> stream(Prompt prompt) {
    return chatClient.prompt(prompt).stream().chatResponse();
  }

  public byte[] generateImage(GenerateImageInput input) throws GenerateImageException {
    GenerateImageResponse result =
        restTemplate.postForObject(GENERATE_ENDPOINT, input, GenerateImageResponse.class);
    if (result == null || result.images().isEmpty()) {
      throw new GenerateImageException("Failed to complete operation");
    }

    String base64Image = result.images().getFirst();
    return Base64.getDecoder().decode(base64Image);
  }
}
