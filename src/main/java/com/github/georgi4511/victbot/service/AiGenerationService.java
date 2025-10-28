package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.exception.GenerateImageException;
import com.github.georgi4511.victbot.model.GenerateImageInput;
import com.github.georgi4511.victbot.model.GenerateImageResponse;
import java.util.Base64;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@ConditionalOnProperty(value = "vict.stable-diffusion.active", havingValue = "true")
public class AiGenerationService {
  private static final String GENERATE_ENDPOINT = "/sdapi/v1/txt2img";
  private final OllamaApi ollamaApi;
  private final RestTemplate restTemplate;

  public AiGenerationService(
      @Value("${vict.stable-diffusion.url:}") String stableDiffusionUrl,
      OllamaApi.Builder builder,
      RestTemplateBuilder restTemplateBuilder) {
    this.ollamaApi = builder.build();
    this.restTemplate = restTemplateBuilder.rootUri(stableDiffusionUrl).build();
  }

  public String call(String message) {
    return ollamaApi.chat(OllamaApi.ChatRequest.builder(message).build()).message().content();
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
