package com.vanderlelie.frontend.services;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.vanderlelie.frontend.enums.RequestMethod;
import com.vanderlelie.frontend.models.Order;
import com.vanderlelie.frontend.models.User;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import com.google.gson.Gson;

public class RequestService {
    private final String API_URL = "http://localhost:8080/api";
    private final int API_TIMEOUT = 5;
    private HttpClient client;
    private String token = "";
    static RequestService requestService;

    public static RequestService getInstance() {
        if (requestService == null){
            requestService = new RequestService();
        }
        return requestService;
    }

    public RequestService() {
        client = HttpClient.newBuilder()
                .version(Version.HTTP_2)
                .followRedirects(Redirect.NORMAL)
                .build();
    }

    public User loginUser(String username, String password) throws Exception {
        try {
            this.token = getToken(username, password);
            System.out.println("token");
            System.out.println(token);

            return this.makeRequest(RequestMethod.GET, "/users/@me", new User());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong while getting token");
        }

        throw new Exception("Something failed while getting logged in user");
    }

    private String getToken(String username, String password) throws Exception {
        JsonObject payload = new JsonObject();
        payload.addProperty("username", username);
        payload.addProperty("password", password);

        JsonObject actualResponse = this.makeRequest(RequestMethod.POST, "/authenticate", new JsonObject(), payload.toString());
        return actualResponse.get("token").getAsString();
    }

    public User getUser() throws Exception {
        return this.makeRequest(RequestMethod.GET, "/users", new User());
    }

    public Order getOrder() throws Exception {
        return this.makeRequest(RequestMethod.GET, "/orders", new Order());
    }

    private <R> R makeRequest(RequestMethod method, String path, R returnType, String payload) throws Exception {
        System.out.printf("[%s] %s%s \n", method, API_URL, path);

        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(API_URL + path))
                    .header("Authorization", "Bearer" + token)
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(API_TIMEOUT));

            switch (method) {
                case GET -> {
                    requestBuilder.GET();
                }
                case POST -> requestBuilder.POST(HttpRequest.BodyPublishers.ofString(payload));
            }

            HttpRequest request = requestBuilder.build();

            CompletableFuture<HttpResponse<String>> responseFuture =
                    client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            CompletableFuture<R> returnedResponse = responseFuture.thenApply((HttpResponse<String> response) -> {
                System.out.println("Response code: " + response.statusCode());
                System.out.println("Response body: " + response.body());

                Type actualReturnType = new TypeToken<R>() {}.getType();
                return parseResponse(response.body(), actualReturnType);
            });

            return returnedResponse.join();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // No payload
    private <R> R makeRequest(RequestMethod method, String path, R returnType) throws Exception {
        return makeRequest(method, path, returnType, "Nothing");
    }

    private <R> R parseResponse(String responseBody, Type returnType) {
        System.out.println("parsing as " + returnType);
        return new Gson().fromJson(responseBody, returnType);
    }
}
