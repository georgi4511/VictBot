package com.github.georgi4511.victbot.model;

import java.util.List;

public record GenerateImageResponse(List<String> images, Object parameters, String info) {
}
