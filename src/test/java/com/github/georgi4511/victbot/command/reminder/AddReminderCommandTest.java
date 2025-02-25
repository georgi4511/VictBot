package com.github.georgi4511.victbot.command.reminder;

import com.github.georgi4511.victbot.service.ReminderService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddReminderCommandTest {

  private static final Logger log = LoggerFactory.getLogger(AddReminderCommandTest.class);

  @Test
  void test_callback_ok() {
    ReminderService reminderServiceMock = mock(ReminderService.class);
    SlashCommandInteractionEvent eventMock = mock(SlashCommandInteractionEvent.class);

    when(eventMock.getOptions()).thenReturn(List.of());
    when(eventMock.getChannelId()).thenReturn("test_channelId");
    Guild guildMock = mock(Guild.class);
    User userMock = mock(User.class);
    when(eventMock.getGuild()).thenReturn(guildMock);
    when(guildMock.getId()).thenReturn("test_guildId");
    when(eventMock.getUser()).thenReturn(userMock);
    when(userMock.getId()).thenReturn("test_userId");

    ArgumentCaptor<String> entityCaptor = ArgumentCaptor.forClass(String.class);

    when(eventMock.reply(entityCaptor.capture())).thenReturn(mock(ReplyCallbackAction.class));

    AddReminderCommand addReminderCommand = spy(new AddReminderCommand(reminderServiceMock));

    addReminderCommand.callback(eventMock);

    String value = entityCaptor.getValue();
    assertEquals(String.format("Set reminder for: %s",Instant.now().plus(1, ChronoUnit.MINUTES).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL,FormatStyle.MEDIUM))), value);

  }
}
