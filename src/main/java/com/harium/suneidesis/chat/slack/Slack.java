package com.harium.suneidesis.chat.slack;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.harium.chatbot.slack.SlackFile;
import com.harium.chatbot.slack.SlackPersona;
import com.harium.chatbot.slack.SlackSession;
import com.harium.chatbot.slack.SlackUser;
import com.harium.chatbot.slack.events.SlackMessagePosted;
import com.harium.chatbot.slack.impl.SlackSessionFactory;
import com.harium.chatbot.slack.listeners.SlackMessagePostedListener;
import com.harium.suneidesis.chat.Parser;
import com.harium.suneidesis.chat.box.BoxHandler;
import com.harium.suneidesis.chat.input.InputContext;
import com.harium.suneidesis.chat.output.Output;
import com.harium.suneidesis.chat.output.OutputContext;
import com.harium.suneidesis.chat.slack.file.FileMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Slack implements BoxHandler {

    public static final String PARAM_FILES = "slack_files";

    private SlackSession session;

    public Slack(String token) {
        session = SlackSessionFactory.createWebSocketSlackSession(token);
    }

    public void connect() throws IOException {
        session.connect();
    }

    @Override
    public void addParser(Parser parser) {
        session.addMessagePostedListener(new MessageListener(parser));
    }

    @Override
    public void sendMessage(String channel, String message) {
        SlackChannel c = new SlackChannel(channel);
        session.sendMessage(c, message);
    }

    private class MessageListener implements SlackMessagePostedListener {

        Parser parser;

        public MessageListener(Parser parser) {
            this.parser = parser;
        }

        @Override public void onEvent(SlackMessagePosted event, SlackSession session) {
            SlackUser messageSender = event.getSender();
            SlackPersona me = session.sessionPersona();

            if (me.getId().equals(messageSender.getId())) {
                return;
            }

            String message = event.getMessageContent();
            com.harium.chatbot.slack.SlackChannel channel = event.getChannel();

            InputContext context = buildContext(event, message);
            parser.parse(context, new SlackOutput(channel));
        }

        private InputContext buildContext(SlackMessagePosted event, String message) {
            InputContext context = new InputContext();
            context.setSentence(message);
            context.getProperties().put(InputContext.USER_ID, event.getSender().getId());
            context.getProperties().put(InputContext.USER_USERNAME, event.getSender().getUserName());
            context.getProperties().put(InputContext.USER_NAME, event.getSender().getRealName());
            context.getProperties().put(InputContext.CHANNEL_ID, event.getChannel().getId());
            context.getProperties().put(InputContext.CHANNEL_NAME, event.getChannel().getName());

            context.getProperties().put(PARAM_FILES, parseFiles(event.getJsonSource()));

            return context;
        }
    }

    List<SlackFile> parseFiles(String jsonSource) {
        Gson gson = new Gson();
        JsonObject node = gson.fromJson(jsonSource, JsonObject.class);
        JsonArray files = node.getAsJsonArray("files");

        if (files != null) {
            List<SlackFile> result = new ArrayList<>();
            for (JsonElement element : files) {
                JsonObject elementNode = element.getAsJsonObject();
                result.add(FileMapper.mapFrom(elementNode));
            }
            return result;
        }

        return Collections.emptyList();
    }

    private class SlackOutput implements Output {

        private com.harium.chatbot.slack.SlackChannel channel;

        public SlackOutput(com.harium.chatbot.slack.SlackChannel channel) {
            this.channel = channel;
        }

        @Override public void print(String sentence, OutputContext context) {
            session.sendMessage(channel, sentence);
        }

        @Override public void produceFile(String path, String description) {
            try {
                byte[] content = Files.readAllBytes(Paths.get(path));
                session.sendFile(channel, content, description);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Expose Slack methods
    public SlackSession getSession() {
        return session;
    }
}
