import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MessageDestroyer implements Runnable {

    private Twitter twitter;
    private long messageID;

    public MessageDestroyer(Twitter twitter, long messageID) {
        this.twitter = twitter;
        this.messageID = messageID;
    }

    @Override
    public void run() {
        try {
            twitter.destroyDirectMessage(messageID);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
