import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Main {
    public static void main(String[] args) {
        var tf= new TwitterFactory();
        var twitter = tf.getInstance();
        try {
            //twitter.updateStatus("First Bot Post");
            var message = twitter.sendDirectMessage(580637424, "My twitter bot's first direct message");
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}