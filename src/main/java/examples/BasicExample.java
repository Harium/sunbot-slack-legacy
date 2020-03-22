package examples;

import com.harium.suneidesis.chat.box.EchoBox;
import com.harium.suneidesis.chat.box.LanguageBox;
import com.harium.suneidesis.chat.instance.Instance;
import com.harium.suneidesis.chat.slack.Slack;
import java.io.IOException;

public class BasicExample {

  public static void main(String[] args) throws IOException {
    LanguageBox box = new EchoBox();
    Instance bot = new Instance("Precious Bot");
    bot.setLanguageBox(box);

    String token = "MY_TOKEN";

    Slack slack = new Slack(token);
    slack.addInstance(bot);
  }

}
