package com.example.test.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Component
public class BaseRestOutboundProcessor {

    @Autowired
    RestTemplate restTemplate;

    public <T extends Object> ResponseEntity<T> post(String apiUrl, Object request, Class<T> responseClazz,
                                                     Map<String, String> headers)  {
        HttpHeaders httpHeaders= Headers(headers);
        HttpEntity<Object> entity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, responseClazz);
        return responseEntity;
    }

    public <T extends Object> ResponseEntity<T> get(String apiUrl, Class<T> responseClazz,
                                                    Map<String, String> headers) {
        HttpHeaders httpHeaders= Headers(headers);
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, responseClazz);
        return responseEntity;
    }

    public <T extends Object> ResponseEntity<T> getById(String apiUrl, Class<T> responseClazz,
                                                    Map<String, String> headers) {
        HttpHeaders httpHeaders= Headers(headers);
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, responseClazz);
        return responseEntity;
    }

    public <T extends Object> ResponseEntity<T> delete(String apiUrl, Object request, Class<T> responseClazz,
                                                       Map<String, String> headers) {
        HttpHeaders httpHeaders= Headers(headers);
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.DELETE, entity, responseClazz);
        return responseEntity;
    }


    private HttpHeaders Headers(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_UTF8.getType());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((key, value) ->
                    httpHeaders.set(key, value));
        }
        return httpHeaders;
    }
}
