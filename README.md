# **VictBot the DiscordBot**

### **This** is **the Public repository** for a [Spring](https://spring.io) Java<sup>runs on 3 billion devices btw</sup> Discord Bot Application using the [JDA](https://github.com/discord-jda/JDA) library
[![CodeQL](https://github.com/georgi4511/VictBot/actions/workflows/github-code-scanning/codeql/badge.svg)](https://github.com/georgi4511/VictBot/actions/workflows/github-code-scanning/codeql)

## Steps to run

>[!NOTE]
> Add an environment variable BOT_TOKEN with the value of the token gotten from Discord API Dashboard and
> if testing with servers add an enviroment variable BOT_GUILDS with the value of the guild IDs separated by commas

```bash
mvn clean compile
java -jar target\VictBot-X.jar
```
replace X with current version which can be found in the pom.xml or by noting 

## Description

### This is a Java Discord Bot Application that aims to appeal to my interests in regard to APIs and also silly bots. It is not currently planned to be used in prod, Viable but use at own risk.

### Checklist for planned things

- [ ] Query image repos for funny **memes**
- [x] Impressions of the bot
- [x] Reminder function
- [x] Bookmark* functionality 
- [ ] Economy system
- [x] Random art
- [ ] Games?
- [❌] Music
- [x] LLM Integration?
- [x] Stable Diffusion Integration
- [ ] Open Library Books
- [x] Administrative functionality (eg. Ban/Timeout command)
- [ ] Change Icons
- [ ] QR code creator
- [x] etc. API commands
- [x] Separate dev and prod bot!
- [ ] Separate current monolith into at the very least modules (example Bot Module, API Module, Logging Module, AI
  Module et cet.)
