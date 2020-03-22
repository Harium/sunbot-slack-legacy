package examples;

import com.harium.suneidesis.chat.slack.Slack;
import com.harium.suneidesis.instance.Instance;
import com.harium.suneidesis.knowledge.linguistic.core.box.EchoBox;
import com.harium.suneidesis.knowledge.linguistic.core.box.LanguageBox;
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
