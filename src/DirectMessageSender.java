import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Scanner;

public class DirectMessageSender implements Runnable{

    private Twitter twitter;
    private String screenName;
    private String text;

    public DirectMessageSender(Twitter twitter) {
        this.twitter = twitter;
    }

    public void getScreenName() {
        System.out.print("Please enter screen name (@example): ");
        screenName = new Scanner(System.in).nextLine();
    }

    public void getText() {
        System.out.print("Please enter message: ");
        text = new Scanner(System.in).nextLine();
    }

    @Override
    public void run() {
        try {
            twitter.sendDirectMessage(screenName, text);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
