import com.apptastic.rssreader.RssReader;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        final var baseURLDM = "https://news.google.com/news/rss/headlines/section/geo/";
        final var statusURL = "https://news.google.com/news/rss/global";

        var mentionsPath = "previousMentions.txt";
        var tweetsPath = "previousTweets.txt";

        var tf = new TwitterFactory();
        var twitter = tf.getInstance();

        var io = new UserIO();

        var tweeter = new Tweeter(twitter);
        var directMessageSender = new DirectMessageSender(twitter);

        var executor = Executors.newScheduledThreadPool(3);

        var directMessageBot = new DirectMessageBot(twitter, baseURLDM);
        var tweeterBot = new TweeterBot(twitter, statusURL, tweetsPath);
        var mentionBot = new MentionBot(twitter, baseURLDM, mentionsPath);

        var option = -1;

        executor.scheduleAtFixedRate(directMessageBot, 0, 1, TimeUnit.MINUTES);
        executor.scheduleAtFixedRate(tweeterBot, 0, 30, TimeUnit.MINUTES);
        executor.scheduleAtFixedRate(mentionBot, 0, 10, TimeUnit.SECONDS);
        do {
            io.printMenu();
            option = io.getUserInput();
            switch (option) {
                case 0:
                    break;
                case 1:
                    tweeter.getText();
                    new Thread(tweeter).start();
                    break;
                case 2:
                    directMessageSender.getScreenName();
                    directMessageSender.getText();
                    new Thread(directMessageSender).start();
                    break;
                default:
                    System.out.println("No such option");
                    break;
            }
        } while (option != 0);

        executor.shutdownNow();
    }
}