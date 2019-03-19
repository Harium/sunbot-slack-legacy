package com.harium.suneidesis.chat.slack;

import com.harium.chatbot.slack.SlackPersona;
import com.harium.chatbot.slack.SlackSession;
import com.harium.chatbot.slack.SlackUser;
import com.harium.chatbot.slack.events.SlackMessagePosted;
import com.harium.chatbot.slack.impl.SlackSessionFactory;
import com.harium.chatbot.slack.listeners.SlackMessagePostedListener;
import com.harium.suneidesis.instance.Instance;
import com.harium.suneidesis.knowledge.linguistic.core.box.Chatbox;
import com.harium.suneidesis.output.Output;

import java.io.IOException;

public class Slack implements Chatbox {

    SlackSession session;

    public Slack(String token) throws IOException {
        session = SlackSessionFactory.createWebSocketSlackSession(token);
        session.connect();
    }

    @Override
    public void addInstance(Instance instance) {
        session.addMessagePostedListener(new MessageListener(instance));
    }

    @Override
    public void sendMessage(String channel, String message) {
        SlackChannel c = new SlackChannel(channel);
        session.sendMessage(c, message);
    }

    private class MessageListener implements SlackMessagePostedListener {
        Instance instance;

        public MessageListener(Instance instance) {
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

            instance.input(message, new SlackOutput(channel));
        }
    }

    private class SlackOutput implements Output {
        private com.harium.chatbot.slack.SlackChannel channel;

        public SlackOutput(com.harium.chatbot.slack.SlackChannel channel) {
            this.channel = channel;
        }

        @Override
        public void print(String sentence) {
            session.sendMessage(channel, sentence);
        }
    }

}
