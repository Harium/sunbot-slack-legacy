package com.harium.suneidesis.chat.slack;

import com.harium.chatbot.slack.SlackPersona;
import com.harium.chatbot.slack.SlackSession;
import com.harium.chatbot.slack.SlackUser;
import com.harium.chatbot.slack.events.SlackMessagePosted;
import com.harium.chatbot.slack.impl.SlackSessionFactory;
import com.harium.chatbot.slack.listeners.SlackMessagePostedListener;
import com.harium.suneidesis.chat.box.BoxHandler;
import com.harium.suneidesis.chat.box.ChatBox;
import com.harium.suneidesis.chat.input.InputContext;
import com.harium.suneidesis.chat.output.Output;
import com.harium.suneidesis.chat.output.OutputContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Slack implements BoxHandler {

  public static final String PARAM_FILE = "slack_file";
  public static final String PARAM_ATTACHMENTS = "slack_attachments";

  private SlackSession session;

  public Slack(String token) throws IOException {
    session = SlackSessionFactory.createWebSocketSlackSession(token);
    session.connect();
  }

  @Override
  public void addBox(ChatBox instance) {
    session.addMessagePostedListener(new MessageListener(instance));
  }

  @Override
  public void sendMessage(String channel, String message) {
    SlackChannel c = new SlackChannel(channel);
    session.sendMessage(c, message);
  }

  private class MessageListener implements SlackMessagePostedListener {

    ChatBox instance;

    public MessageListener(ChatBox instance) {
      this.instance = instance;
    }

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
      SlackUser messageSender = event.getSender();
      SlackPersona me = session.sessionPersona();

      if (me.getId().equals(messageSender.getId())) {
        return;
      }

      String message = event.getMessageContent();
      com.harium.chatbot.slack.SlackChannel channel = event.getChannel();

      InputContext context = buildContext(event, message);
      instance.input(context, new SlackOutput(channel));
    }

    private InputContext buildContext(SlackMessagePosted event, String message) {
      InputContext context = new InputContext();
      context.setSentence(message);
      context.getProperties().put(InputContext.USER_ID, event.getSender().getId());
      context.getProperties().put(InputContext.USER_USERNAME, event.getSender().getUserName());
      context.getProperties().put(InputContext.USER_NAME, event.getSender().getRealName());
      context.getProperties().put(InputContext.CHANNEL_ID, event.getChannel().getId());
      context.getProperties().put(InputContext.CHANNEL_NAME, event.getChannel().getName());

      context.getProperties().put(PARAM_FILE, event.getSlackFile());
      context.getProperties().put(PARAM_ATTACHMENTS, event.getSlackFile());

      return context;
    }
  }

  private class SlackOutput implements Output {

    private com.harium.chatbot.slack.SlackChannel channel;

    public SlackOutput(com.harium.chatbot.slack.SlackChannel channel) {
      this.channel = channel;
    }

    @Override
    public void print(String sentence, OutputContext context) {
      session.sendMessage(channel, sentence);
    }

    @Override
    public void produceFile(String path, String description) {
      try {
        byte[] content = Files.readAllBytes(Paths.get(path));
        session.sendFile(channel, content, description);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
