package com.github.georgi4511.discord_bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

@SpringBootApplication
public class DiscordBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscordBotApplication.class, args);
	}


	@Value("${discord.token}")
	private String TOKEN;

	@Bean
	public JDA jda() throws LoginException {

		EnumSet<GatewayIntent> intents = EnumSet.of(
				GatewayIntent.GUILD_MESSAGES,
				GatewayIntent.GUILD_VOICE_STATES,
				GatewayIntent.MESSAGE_CONTENT,
				GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
				GatewayIntent.SCHEDULED_EVENTS,
				GatewayIntent.GUILD_PRESENCES
		);

		return JDABuilder.create(TOKEN,intents)
				.setActivity(Activity.listening("Chilling...killing"))
				.setStatus(OnlineStatus.DO_NOT_DISTURB)
				.enableCache(CacheFlag.VOICE_STATE)
				.build();
	}
}
