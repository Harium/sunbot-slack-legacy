# sunbot-slack
Plugin to turn your Suneidesis Chatbot into a Slack Bot

## Maven
```xml
<dependency>
    <groupId>com.harium.suneidesis.sunbot</groupId>
    <artifactId>slack</artifactId>
    <version>1.0.2</version>
</dependency>
```

## How to use it

```
    LanguageBox box = new EchoBox(); // Use your own box
    Instance bot = new Instance("Precious Bot");
    bot.setLanguageBox(box);

    String token = "MY_TOKEN";

    Slack slack = new Slack(token);
    slack.addInstance(bot);
```
