package com.harium.suneidesis.chat.slack;

import org.junit.Assert;
import org.junit.Test;

public class SlackChannelTest {

    @Test
    public void testInit() {
        SlackChannel channel = new SlackChannel("123");
        Assert.assertEquals("123", channel.getId());
    }

}
