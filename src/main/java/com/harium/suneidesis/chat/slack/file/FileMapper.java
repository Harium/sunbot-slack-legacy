package com.harium.suneidesis.chat.slack.file;

import com.google.gson.JsonObject;
import com.harium.chatbot.slack.SlackFile;

public class FileMapper {

    public static SlackFile mapFrom(JsonObject node) {
        SlackFile file = new SlackFile();
        file.setId(asString(node, "id"));
        file.setName(asString(node, "name"));
        file.setTitle(asString(node, "title"));
        file.setMimetype(asString(node, "mimetype"));
        file.setFiletype(asString(node, "filetype"));
        file.setUrlPrivate(asString(node, "url_private"));
        file.setUrlPrivateDownload(asString(node, "url_private_download"));
        file.setPermalink(asString(node, "permalink"));
        file.setPermalinkPublic(asString(node, "permalink_public"));

        return file;
    }

    private static long asLong(JsonObject node, String attr) {
        if (node.has(attr)) {
            return node.get(attr).getAsLong();
        }
        return 0;
    }

    private static String asString(JsonObject node, String attr) {
        if (node.has(attr)) {
           return node.get(attr).getAsString();
        }
        return "";
    }

}
