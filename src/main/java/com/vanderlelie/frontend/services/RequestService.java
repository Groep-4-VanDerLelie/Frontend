package com.vanderlelie.frontend.services;

import com.google.gson.reflect.TypeToken;
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

    public User getUser() throws Exception {
        return this.makeAuthGetRequest("/users/all", new User());
    }

    public Order getOrder() throws Exception {
        return this.makeAuthGetRequest("/orders/all", new Order());
    }

    private <R> R makeAuthGetRequest(String path, R returnType) throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL + "/" + path))
                    .GET()
                    .header("Authorization", "token")
                    .timeout(Duration.ofSeconds(API_TIMEOUT))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture =
                    client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            CompletableFuture<R> returnedResponse = responseFuture.thenApply((HttpResponse<String> response) -> {
                System.out.println("Response code: " + response.statusCode());

                Type actualReturnType = new TypeToken<R>() {}.getType();
                return parseResponse(response.body(), actualReturnType);
            });

            return returnedResponse.join();
        } catch (Exception e) {
            throw e;
        }
    }

    private <R> R parseResponse(String responseBody, Type returnType) {
        return new Gson().fromJson(responseBody, returnType);
    }
}
