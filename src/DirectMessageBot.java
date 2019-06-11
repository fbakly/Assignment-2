import twitter4j.Twitter;

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


    @Override
    public void run() {
        var hashtagsText = new ArrayList<String>();

        try {
            var messages = twitter.getDirectMessages(3);
            for (var message : messages) {
                new Thread(new DirectMessageBotSlave(twitter, message, baseURL)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
