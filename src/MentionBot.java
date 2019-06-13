import twitter4j.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MentionBot implements Runnable {

    private Twitter twitter;
    private long lastMentionID;
    private String baseURL;
    private HashSet<Long> previousMentionsID;
    private ExecutorService pool = Executors.newCachedThreadPool();
    private String path;
    private Cacher cacher;

    public MentionBot(Twitter twitter, String baseURL, String path) {
        this.twitter = twitter;
        this.baseURL = baseURL;
        this.lastMentionID = -1;
        this.previousMentionsID = new HashSet<>();
        this.path = path;
        this.cacher = new Cacher(this.path);
    }

    @Override
    public void run() {
        try {

            if (new File(path).exists())
                this.previousMentionsID = cacher.readFile();

            var mentions = twitter.getMentionsTimeline(new Paging(1));
            mentions.stream().filter(status -> !previousMentionsID.contains(status.getId())).forEach(status -> {
                pool.submit(new MentionBotSlave(twitter, status, baseURL));
            });
            previousMentionsID.addAll(mentions.stream().map(status -> status.getId()).collect(Collectors.toSet()));

            var sb = new StringBuilder();

            for (var mention : previousMentionsID) {
                sb.append(mention.toString());
                sb.append("\n");
            }
            cacher.writeFile(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
