package com.github.georgi4511.victbot.command.ai;

import com.github.georgi4511.victbot.model.GenerateImageInput;
import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.AiGenerationService;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.utils.AttachedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Component
public class AiArtCommand extends VictCommand {
    private static final Logger log = LoggerFactory.getLogger(AiArtCommand.class);
    private final AiGenerationService aiGenerationService;
    private SlashCommandData data;
    private String name;
    private String description;

    public AiArtCommand(AiGenerationService aiGenerationService) {
        this.aiGenerationService = aiGenerationService;
        this.name = "ai-art";
        this.description = "create ai image using stable diffusion";
        this.data =
                Commands.slash(this.name, this.description)
                        .addOption(OptionType.STRING, "prompt", "the prompt sent to the bot", true)
                        .addOption(OptionType.STRING, "negative-prompt", "the negative prompt sent to the bot")
                        .addOption(OptionType.STRING, "sampler-name", "sampler to use")
                        .addOption(OptionType.INTEGER, "steps", "steps for image generation")
                        .addOption(OptionType.INTEGER, "cfg-scale", "cfg-scale for image generation")
                        .addOption(OptionType.INTEGER, "width", "width for image generation")
                        .addOption(OptionType.INTEGER, "height", "height for image generation")
        ;
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        try {
            
            GenerateImageInput imageInput = getGenerateImageInput(event);

            byte[] image = aiGenerationService.generateImage(imageInput);

            event.getHook().editOriginalAttachments(AttachedFile.fromData(image, "o.png")).queue();

        } catch (Exception e) {
            log.info(e.getMessage());
            event.getHook().editOriginal("Sorry command failed to execute").queue();
        }
    }

    private GenerateImageInput getGenerateImageInput(SlashCommandInteractionEvent event) {

        String prompt = Objects.requireNonNull(event.getOption("prompt")).getAsString();

        OptionMapping negativePromptOption = event.getOption("negative-prompt");
        String negativePrompt = negativePromptOption == null ? "" : negativePromptOption.getAsString();

        OptionMapping samplerNameOption = event.getOption("sampler-name");
        String samplerName = samplerNameOption == null ? "Euler a" : samplerNameOption.getAsString();

        OptionMapping stepsOption = event.getOption("steps");
        Integer steps = stepsOption == null ? 28 : stepsOption.getAsInt();

        OptionMapping widthOption = event.getOption("width");
        Integer width = widthOption == null ? 1024 : widthOption.getAsInt();

        OptionMapping heightOption = event.getOption("height");
        Integer height = heightOption == null ? 1024 : heightOption.getAsInt();

        OptionMapping cfgScaleOption = event.getOption("cfg-scale");
        Integer cfgScale = cfgScaleOption == null ? 5 : cfgScaleOption.getAsInt();

        return new GenerateImageInput(prompt, negativePrompt,
                steps, width, height, cfgScale, samplerName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AiArtCommand that = (AiArtCommand) o;
        return Objects.equals(data, that.data)
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(aiGenerationService, that.aiGenerationService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data, name, description, aiGenerationService);
    }
}
