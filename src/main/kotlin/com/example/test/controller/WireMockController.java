package com.example.test.controller;

import com.example.test.model.RestResponse;
import com.example.test.model.WSProduct;
import com.example.test.util.BaseRestOutboundProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WireMockController {
    @Autowired
    BaseRestOutboundProcessor baseRestOutboundProcessor;

    @Value("${base.url}")
    String baseUrl;


    @PostMapping("/user")
    public ResponseEntity<RestResponse> saveProductDetails(@RequestBody WSProduct request) {
        Map<String, String> headers = new HashMap<>();
        headers.put("access-key", UUID.randomUUID()
                .toString());
        headers.put("secret-key", UUID.randomUUID()
                .toString());
        ResponseEntity<WSProduct> responseEntity = baseRestOutboundProcessor.post(baseUrl + "api/user", request, WSProduct.class,
                headers);
        return ResponseEntity.ok(responseEntity != null ? responseEntity.getBody() : null);
    }

    @GetMapping("/product")
    public ResponseEntity<RestResponse> getProductData() {
        Map<String, String> headers = new HashMap<>();
        headers.put("access-key", UUID.randomUUID()
                .toString());
        headers.put("secret-key", UUID.randomUUID()
                .toString());
        ResponseEntity<WSProduct> responseEntity = baseRestOutboundProcessor.get(baseUrl + "/api/product", WSProduct.class,
                headers);
        return ResponseEntity.ok(responseEntity != null ? responseEntity.getBody() : null);
    }

    @GetMapping("/product/id")
    public ResponseEntity<RestResponse> getProductDataById(@PathVariable("id") String id) {
        Map<String, String> headers = new HashMap<>();
        headers.put("access-key", UUID.randomUUID()
                .toString());
        headers.put("secret-key", UUID.randomUUID()
                .toString());
        ResponseEntity<WSProduct> responseEntity = baseRestOutboundProcessor.getById(baseUrl + "api/product/id?id=" + id, WSProduct.class,
                headers);
        return ResponseEntity.ok(responseEntity != null ? responseEntity.getBody() : null);
    }
}
