import twitter4j.TwitterFactory;

public class Main {
    public static void main(String[] args) {

        var tf = new TwitterFactory();
        var twitter = tf.getInstance();
        var io = new UserIO();
        var tweeter = new Tweeter(twitter);
        var directMessageSender = new DirectMessageSender(twitter);
        var option = -1;
        do {
            io.printMenu();
            option = io.getUserInput();
            switch (option) {
                case 0 :
                    break;
                case 1 :
                    tweeter.getText();
                    new Thread(tweeter).start();
                    break;
                case 2 :
                    directMessageSender.getScreenName();
                    directMessageSender.getText();
                    new Thread(directMessageSender).start();
                    break;
                default :
                    System.out.println("No such option");
                    break;
            }
        } while (option != 0);
    }
}