package com.github.georgi4511.victbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GenerateImageInput(
        String prompt,
        @JsonProperty("negative_prompt") String negativePrompt,
        Integer steps,
        Integer width,
        Integer height,
        @JsonProperty("cfg_scale") Integer cfgScale,
        @JsonProperty("sampler_name") String samplerName) {
}
