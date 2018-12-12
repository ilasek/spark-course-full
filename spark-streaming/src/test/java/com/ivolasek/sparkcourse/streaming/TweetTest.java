package com.ivolasek.sparkcourse.streaming;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TweetTest {

    private static DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Test
    public void testJsonSerialization() throws IOException, ParseException {
        ObjectMapper om = new ObjectMapper();
        Date date = formatter.parse("10-12-2018 19:05:06");
        String tweetJson = om.writeValueAsString(new Tweet("tweet-id", date, "Tweet text"));
        Tweet tweet = om.readValue(tweetJson, Tweet.class);
        assertEquals(date.getTime(), tweet.getCreatedAt().getTime());
    }

}