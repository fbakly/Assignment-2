import com.apptastic.rssreader.RssReader;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        var tf = new TwitterFactory();
        var twitter = tf.getInstance();
        try {
            var messagesSent = twitter.getDirectMessages(1);
            for (var index : messagesSent) {
                var hashtags = index.getHashtagEntities();
                System.out.print(index.getRecipientId() + " got a message from " + index.getSenderId() + " " + index.getText());
                for (var hashtag : hashtags) {
                    System.out.print(hashtag.getText() + " ");
                }
                System.out.println();
            }
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}