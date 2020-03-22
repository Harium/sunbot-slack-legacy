# sunbot-slack
Plugin to turn your Suneidesis Chatbot into a Slack Bot

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.harium.suneidesis.sunbot/slack/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.harium.suneidesis.sunbot/slack/)

## How to use it

```
    LanguageBox box = new EchoBox(); // Use your own box
    Instance bot = new Instance("Precious Bot");
    bot.setLanguageBox(box);

    String token = "MY_TOKEN";

    Slack slack = new Slack(token);
    slack.addInstance(bot);
```
