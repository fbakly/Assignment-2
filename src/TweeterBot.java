import twitter4j.Twitter;

import java.io.IOException;
import java.util.stream.Collectors;

public class TweeterBot implements Runnable {

    private Twitter twitter;
    private String url;

    public TweeterBot(Twitter twitter, String url) {
        this.twitter = twitter;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            var rssReader = new FeedReader();
            var feed = rssReader.read(url);
            var latestArticle = feed.collect(Collectors.toList()).get(0);
            twitter.updateStatus(latestArticle.getTitle().orElse("") + "\n" + latestArticle.getLink().orElse(""));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
