package OpenWebHook.Endpoints;

import OpenWebHook.API.EventMessage;
import OpenWebHook.API.PayloadTypeProperty;
import OpenWebHook.Events.WebHookEvent;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An Endpoint class that represents a webhook endpoint that events should be sent to.
 */
public class Endpoint {
    private static final Logger log = LoggerFactory.getLogger(Endpoint.class);
    /**
     * The list of active endpoints to send events to.
     */
    public static List<Endpoint> endpoints = new ArrayList<>();
    /**
     * The URL of this endpoint.
     */
    public final String URL;
    /**
     * The HTTP client used to send webhook payloads with.
     */
    private final OkHttpClient httpClient = new OkHttpClient();
    /**
     * The MediaType of the payload.
     */
    private static final MediaType mediaType = MediaType.parse("application/json");

    /**
     * Creates a new webhook endpoint from a given url.
     * @param url The URL to create an endpoint for.
     */
    public Endpoint(String url) {
        URL = url;
    }

    /**
     * Sends the given payload to this webhook endpoint.
     * @param payload The JSON payload to send.
     */
    public void Send(String payload) {
        log.info("payload: " + payload);
        RequestBody body = RequestBody.create(mediaType, payload);

        Request request = new Request.Builder()
                .url(URL)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }

    /**
     * Updates the list of active endpoints from a list of URLs.
     * @param urls The URLs to make endpoints for.
     */
    public static void BuildFromURLs(List<String> urls) {
        List<Endpoint> newEndpoints = new ArrayList<>();
        for (String url : urls) {
            Endpoint newEndpoint = new Endpoint(url);
            newEndpoints.add(newEndpoint);
        }
        endpoints = newEndpoints;
    }

    /**
     * Sends the given WebHookEvent to all applicable webhook endpoints.
     * @param event The WebHookEvent to send.
     */
    public static void SendEvent(WebHookEvent event) {
        String message = new EventMessage(event).AsPayload();

        for (Endpoint endpoint : endpoints) {
            endpoint.Send(message);
        }
    }

}
