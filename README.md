# sunbot-slack
Plugin to turn your Suneidesis Chatbot into a Slack Bot

## Maven
```xml
<dependency>
    <groupId>com.harium.suneidesis.sunbot</groupId>
    <artifactId>slack</artifactId>
    <version>1.0.0</version>
</dependency>
```

## How to use it

```
LanguageBox box = new MyLanguageBox();
Instance bot = new Instance("Precious Bot");
bot.setLanguageBox(box);

String token = "MY_TOKEN;

Slack discord = new Slack(token);
slack.addInstance(bot);
```
