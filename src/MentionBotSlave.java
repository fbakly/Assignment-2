import com.apptastic.rssreader.Item;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MentionBotSlave implements Runnable {
    private Twitter twitter;
    private Status mention;
    private String baseURL;

    MentionBotSlave(Twitter twitter, Status mention, String baseURL) {
        this.twitter = twitter;
        this.mention = mention;
        this.baseURL = baseURL;
    }

    @Override
    public void run() {
        var hashtagText = Arrays.asList(mention.getHashtagEntities()).get(0).getText();
        var replyUsername = "@" + mention.getUser().getScreenName();
        var feedReader = new FeedReader();
        Stream<Item> feed = null;
        try {
            feed = feedReader.read(baseURL + hashtagText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var latestArticle = feed.collect(Collectors.toList()).get(0);
        try {
            twitter.updateStatus("Hey " + replyUsername + " here is the latest news in " + hashtagText + "\n" +
                    latestArticle.getTitle().orElse("") + "\n" + latestArticle.getLink().orElse(""));
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }


}
