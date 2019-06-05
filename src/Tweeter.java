import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Scanner;

public class Tweeter implements Runnable {

    private String text;
    private Twitter twitter;

    public Tweeter(Twitter twitter) {
        this.twitter = twitter;
    }

    public void getText() {
        System.out.print("Please enter status: ");
        text = new Scanner(System.in).nextLine();
    }

    @Override
    public void run() {
        try {
            twitter.updateStatus(text);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
