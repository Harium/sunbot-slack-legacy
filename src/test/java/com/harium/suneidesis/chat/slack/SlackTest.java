package com.harium.suneidesis.chat.slack;

import com.harium.chatbot.slack.SlackFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class SlackTest {

    private Slack slack;

    @Before
    public void setUp() {
        slack = new Slack("");
    }

    @Test
    public void testParseFiles() {
        String jsonSource = "{\"type\":\"message\",\"text\":\"File Description\",\"files\":[{\"id\":\"F0000BBB1GF\",\"created\":1594164077,\"timestamp\":1594164077,\"name\":\"example.csv\",\"title\":\"example.csv\",\"mimetype\":\"text/csv\",\"filetype\":\"csv\",\"pretty_type\":\"CSV\",\"user\":\"U111AAA12SS\",\"editable\":true,\"size\":8,\"mode\":\"snippet\",\"is_external\":false,\"external_type\":\"\",\"is_public\":false,\"public_url_shared\":false,\"display_as_bot\":false,\"username\":\"\",\"url_private\":\"https://files.slack.com/files-pri/ABCDE/example.csv\",\"url_private_download\":\"https://files.slack.com/files-pri/ABCDE/download/example.csv\",\"permalink\":\"https://myslack.slack.com/files/U111AAA12SS/F0000BBB1GF/example.csv\",\"permalink_public\":\"https://slack-files.com/T1BCL0F2S-F0000BBB1GF-a20a5a5a85\",\"edit_link\":\"https://myslack.slack.com/files/U111AAA12SS/F0000BBB1GF/example.csv/edit\",\"preview\":\"a,b\\nc,d\\n\",\"preview_highlight\":\"<div class=\\\"CodeMirror cm-s-default CodeMirrorServer\\\" oncopy=\\\"if(event.clipboardData){event.clipboardData.setData('text/plain',window.getSelection().toString().replace(/\\\\u200b/g,''));event.preventDefault();event.stopPropagation();}\\\">\\n<div class=\\\"CodeMirror-code\\\">\\n<div><pre>a,b</pre></div>\\n<div><pre>c,d</pre></div>\\n<div><pre></pre></div>\\n</div>\\n</div>\\n\",\"lines\":3,\"lines_more\":0,\"preview_is_truncated\":false,\"is_starred\":false,\"has_rich_preview\":false}],\"upload\":false,\"blocks\":[{\"type\":\"rich_text\",\"block_id\":\"cYuDj\",\"elements\":[{\"type\":\"rich_text_section\",\"elements\":[{\"type\":\"text\",\"text\":\"File Description\"}]}]}]}";

        List<SlackFile> files = slack.parseFiles(jsonSource);

        SlackFile first = files.get(0);
        Assert.assertEquals("example.csv", first.getName());
        Assert.assertEquals("csv", first.getFiletype());
    }

}
