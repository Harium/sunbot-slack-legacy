package examples;

import com.harium.suneidesis.chat.box.ChatBox;
import com.harium.suneidesis.chat.box.EchoBox;
import com.harium.suneidesis.chat.slack.Slack;
import java.io.IOException;

public class BasicExample {

  public static void main(String[] args) throws IOException {
    ChatBox bot = new EchoBox();

    String token = "MY_TOKEN";
    Slack slack = new Slack(token);
    slack.addBox(bot);
  }

}
