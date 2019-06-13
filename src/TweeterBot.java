import twitter4j.Twitter;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.stream.Collectors;

public class TweeterBot implements Runnable {

    private Twitter twitter;
    private String url;
    private String path;
    private Cacher cacher;
    private String lastPostedArticle;

    public TweeterBot(Twitter twitter, String url, String path) {
        this.twitter = twitter;
        this.url = url;
        this.path = path;
        this.cacher = new Cacher(this.path);
        this.lastPostedArticle = "";
    }

    @Override
    public void run() {
        try {
            var rssReader = new FeedReader();
            var feed = rssReader.read(url);
            var latestArticle = feed.collect(Collectors.toList()).get(0);

            if (new File(this.path).exists())
                lastPostedArticle = cacher.getLastArticle();

            if (!lastPostedArticle.equals(latestArticle.getGuid().get())) {
                twitter.updateStatus(latestArticle.getTitle().orElse("") + "\n" + latestArticle.getLink().orElse(""));
                cacher.writeFile(latestArticle.getGuid().get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
