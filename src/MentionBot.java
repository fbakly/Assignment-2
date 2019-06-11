import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MentionBot implements Runnable {
    private Twitter twitter;
    private long lastMentionID;
    private String baseURL;

    public MentionBot(Twitter twitter, String baseURL) {
        this.twitter = twitter;
        this.baseURL = baseURL;
        this.lastMentionID = -1;
    }

    @Override
    public void run() {
        try {
            var mentions = twitter.getMentionsTimeline();
            var latestMention = mentions.get(0);
            if (latestMention.getId() != lastMentionID) {
                lastMentionID = latestMention.getId();
                var hashtagText = Arrays.asList(latestMention.getHashtagEntities()).get(0).getText();
                var replyUsername = "@" + latestMention.getUser().getScreenName();
                var feedReader = new FeedReader();
                var feed = feedReader.read(baseURL + hashtagText);
                var latestArticle = feed.collect(Collectors.toList()).get(0);
                twitter.updateStatus("Hey " + replyUsername + " here is the latest news in " + hashtagText + "\n" +
                        latestArticle.getTitle().orElse("") + "\n" + latestArticle.getLink().orElse(""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
