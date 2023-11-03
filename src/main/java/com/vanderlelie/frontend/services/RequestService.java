package com.vanderlelie.frontend.services;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.vanderlelie.frontend.enums.RequestMethod;
import com.vanderlelie.frontend.models.*;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.vanderlelie.frontend.models.responses.GenericResponse;
import com.vanderlelie.frontend.models.responses.TokenResponse;

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

            return this.makeRequest(RequestMethod.GET, "/users/@me", User.class);
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

        TokenResponse tokenResponse = this.makeRequest(RequestMethod.POST, "/authenticate", TokenResponse.class, payload.toString());
        return tokenResponse.getToken();
    }

    public User getUser() throws Exception {
        return this.makeRequest(RequestMethod.GET, "/users", User.class);
    }

    public Order getOrder() throws Exception {
        return this.makeRequest(RequestMethod.GET, "/orders", Order.class);
    }

    public Log[] getLogs() throws Exception {
        return this.makeRequest(RequestMethod.GET, "/logs", Log[].class);
    }

    public Customer[] getCustomers() throws Exception {
        return  this.makeRequest(RequestMethod.GET, "/customers",Customer[].class);
    }


    public Packaging[] getAllPackaging() throws Exception {
        return this.makeRequest(RequestMethod.GET, "/packagings", Packaging[].class);
    }

    private <R> R makeRequest(RequestMethod method, String path, Class<R> returnType, String payload) throws Exception {
        System.out.printf("[%s] %s%s \n", method, API_URL, path);

        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(API_URL + path))
                    .header("Authorization", "Bearer " + token)
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

                GenericResponse<R> responseObject = parseResponse(response.body(), GenericResponse.class);
                if (responseObject.getCode() == null || responseObject.getPayload() == null) {
                    System.out.println("Missing code or payload");
                }

                return parseResponse(new Gson().toJson(responseObject.getPayload()), returnType);
            });

            return returnedResponse.join();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // No payload
    private <R> R makeRequest(RequestMethod method, String path, Class<R> returnType) throws Exception {
        return makeRequest(method, path, returnType, "Nothing");
    }

    private <R> R parseResponse(String responseBody, Type returnType) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson.fromJson(responseBody, returnType);
    }
}
