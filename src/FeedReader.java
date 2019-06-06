import com.apptastic.rssreader.RssReader;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class FeedReader extends RssReader {

    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15L)).followRedirects(HttpClient.Redirect.ALWAYS).build();


    protected CompletableFuture<HttpResponse<InputStream>> sendAsyncRequest(String url) {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(15L)).header("Accept-Encoding", "gzip").GET().build();
        return this.httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofInputStream());
    }
}
