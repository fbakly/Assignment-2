import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;

public class DirectMessageBot implements Runnable {

    private Twitter twitter;
    private String baseURL;
    private int userInput;

    public DirectMessageBot(Twitter twitter, String url, int userInput) {
        this.userInput = userInput;
        this.twitter = twitter;
        this.baseURL = url;
    }

    public void checkUserInput(int userInput) {
        this.userInput = userInput;
    }

    @Override
    public void run() {
        while (userInput != 0) {
            var hashtagsText = new ArrayList<String>();

            try {
                var messages = twitter.getDirectMessages(5);
                for (var message : messages) {
                    new Thread(new DirectMessageBotSlave(twitter, message, baseURL)).start();
                }
                Thread.sleep(30000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
