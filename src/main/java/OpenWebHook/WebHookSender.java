package OpenWebHook;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WebHookSender {
    /**
     * The HTTP client used to send the webhook requests.
     */
    private final OkHttpClient httpClient = new OkHttpClient();

    /**
     * Sends the given payload to the provided webhook endpoints.
     * @param payload The JSON payload to send.
     */
    public void Send(String payload) {
        String webhookUrl = "http://peebot.blackcoyote.net:9000"; // Your webhook URL

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, payload);

        Request request = new Request.Builder()
                .url(webhookUrl)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        httpClient.newCall(request);
    }


}
