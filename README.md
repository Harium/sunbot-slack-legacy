# sunbot-slack
Plugin to turn your Suneidesis Chatbot into a Slack Bot

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.harium.suneidesis.sunbot/slack/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.harium.suneidesis.sunbot/slack/)

## How to use it

```
    ChatBox bot = new EchoBox(); // Use your own box
    
    String token = "MY_TOKEN";
    Slack slack = new Slack(token);
    slack.addBox(bot);
```
