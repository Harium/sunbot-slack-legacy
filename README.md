# sunbot-slack (legacy)
Plugin to turn your Suneidesis Chatbot into a Slack (Legacy) Bot 

## Disclaimer
This library only works with classic Apps in Slack.

Legacy apps are deprecated and is not possible to create tokens anymore.

Use the new library instead: https://github.com/Harium/sunbot-slack

If you have a token, you can still use this library:
```
<dependency>
    <groupId>com.harium.suneidesis.sunbot</groupId>
    <artifactId>slack</artifactId>
    <version>1.4.0</version>
</dependency>
```

## How to use it

```
    Parser bot = new EchoBox(); // Use your own parser

    String token = "MY_TOKEN";
    Slack slack = new Slack(token);
    slack.connect();
    slack.addParser(bot);
```
