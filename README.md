# sunbot-slack
Plugin to turn your Suneidesis Chatbot into a Slack Bot

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.harium.suneidesis.sunbot/slack/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.harium.suneidesis.sunbot/slack/)

## Disclaimer
This library only works with classic Apps in Slack.

Create a classic Bot App using the link: https://api.slack.com/apps?new_classic_app=1

## How to use it

```
    ChatBox bot = new EchoBox(); // Use your own box
    
    String token = "MY_TOKEN";
    Slack slack = new Slack(token);
    slack.addBox(bot);
```
