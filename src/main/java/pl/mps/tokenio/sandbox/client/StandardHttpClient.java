package pl.mps.tokenio.sandbox.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pl.mps.tokenio.sandbox.exception.ConnectionException;
import pl.mps.tokenio.sandbox.exception.SerializingException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Headers.ACCESS_TOKEN;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Headers.AUTHORIZATION;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Headers.CONTENT_TYPE;

public class StandardHttpClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String apiKey;

    public StandardHttpClient(String apiKey) {
        httpClient = HttpClientBuilder.create().build();
        objectMapper = new ObjectMapper();
        this.apiKey = apiKey;
    }

    public <R> R get(String url, String accessToken, Class<R> responseClass) {
        HttpGet request = new HttpGet(url);
        addDefaultHeaders(request);
        request.setHeader(ACCESS_TOKEN, accessToken);
        try {
            return objectMapper.readValue(EntityUtils.toString(httpClient.execute(request).getEntity()), responseClass);
        } catch (IOException e) {
            throw new ConnectionException("get", url, responseClass);
        }
    }

    public <R> R get(String url, Class<R> responseClass) {
        HttpGet request = new HttpGet(url);
        addDefaultHeaders(request);
        try {
            return objectMapper.readValue(EntityUtils.toString(httpClient.execute(request).getEntity()), responseClass);
        } catch (IOException e) {
            throw new ConnectionException("get", url, responseClass);
        }
    }

    public <P, R> R post(String url, P payload, Class<R> responseClass) {
        HttpPost request = new HttpPost(url);
        addDefaultHeaders(request);
        try {
            request.setEntity(new StringEntity(objectMapper.writeValueAsString(payload)));
            return objectMapper.readValue(EntityUtils.toString(httpClient.execute(request).getEntity()), responseClass);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new SerializingException("Cannot serialize payload: " + payload.toString());
        } catch (IOException e) {
            throw new ConnectionException("get", url, responseClass);
        }
    }

    public <P, R> R put(String url, P payload, Class<R> responseClass) {
        HttpPut request = new HttpPut(url);
        addDefaultHeaders(request);
        try {
            request.setEntity(new StringEntity(objectMapper.writeValueAsString(payload)));
            if (Void.class.equals(responseClass)) {
                HttpResponse execute = httpClient.execute(request);
                int statusCode = execute.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new IllegalStateException("Could not store token request");
                }
            }
            return objectMapper.readValue(EntityUtils.toString(httpClient.execute(request).getEntity()), responseClass);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new SerializingException("Cannot serialize payload: " + payload.toString());
        } catch (IOException e) {
            throw new ConnectionException("get", url, responseClass);
        }
    }

    private <T extends HttpRequestBase> void addDefaultHeaders(T request) {
        request.setHeader(CONTENT_TYPE, "application/json");
        request.setHeader(AUTHORIZATION, getApiKey());
    }

    private String getApiKey() {
        return "Basic " + apiKey;
    }
}
