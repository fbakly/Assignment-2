import twitter4j.Twitter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DirectMessageBot implements Runnable {

    private Twitter twitter;
    private String baseURL;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public DirectMessageBot(Twitter twitter, String url) {
        this.twitter = twitter;
        this.baseURL = url;
    }


    @Override
    public void run() {
        var hashtagsText = new ArrayList<String>();
        try {
            var messages = twitter.getDirectMessages(3);

            for (var message : messages) {
                executor.submit(new DirectMessageBotSlave(twitter, message, baseURL));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
