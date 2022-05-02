package com.example.test.controller;

import com.example.test.model.RestResponse;
import com.example.test.model.WSProduct;
import com.example.test.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestWireMockController {
    @ClassRule
    public static WireMockRule wireMockRule = new WireMockRule(8082);

    @Autowired
    WireMockController controller;

    @Value("${base.url}")
    private String url;

    ObjectMapper objectMapper = new CustomObjectMapper();

    @Before
    public void init() {
        ReflectionTestUtils.setField(controller, "baseUrl", url);
        wireMockRule.resetMappings();
        wireMockRule.resetScenarios();
        wireMockRule.resetRequests();
    }

    @Test
    public void testSaveUserDetails() throws Exception {
        WSProduct expectedResponse = generateWSUserDetails();
        String expectedResponseString = objectMapper.writeValueAsString(expectedResponse);
        wireMockRule.stubFor(WireMock.post(WireMock.urlMatching("/api/user"))
                .willReturn(WireMock.aResponse()
                        .withBody(expectedResponseString)
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type",
                                "application/json;charset=UTF-8")));
        ResponseEntity<RestResponse> responseEntity = controller.saveProductDetails(null);
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getBody() instanceof WSProduct);
        WSProduct actualResponse = (WSProduct) responseEntity.getBody();
        assertNotNull(actualResponse);
        assertUserDetails(expectedResponse, actualResponse);
    }

    @Test
    public void testGetUserData() throws Exception {
        WSProduct expectedResponse = generateWSUserDetails();
        String expectedResponseString = objectMapper.writeValueAsString(expectedResponse);
        wireMockRule.stubFor(WireMock.get(WireMock.urlMatching("/api/product"))
                .willReturn(WireMock.aResponse()
                        .withBody(expectedResponseString)
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type",
                                "application/json;charset=UTF-8")));
        ResponseEntity<RestResponse> responseEntity = controller.getProductData();
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getBody() instanceof WSProduct);
        WSProduct actualResponse = (WSProduct) responseEntity.getBody();
        assertNotNull(actualResponse);
        assertUserDetails(expectedResponse, actualResponse);
    }
    @Test
    public void testGetUserDataById() throws Exception {
        WSProduct expectedResponse = generateWSUserDetails();
        String id = UUID.randomUUID()
                .toString();
        String expectedResponseString = objectMapper.writeValueAsString(expectedResponse);
        wireMockRule.stubFor(WireMock.get(WireMock.urlEqualTo("/api/product/id?id=" + id))
                .willReturn(WireMock.aResponse()
                        .withBody(expectedResponseString)
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type",
                                "application/json;charset=UTF-8")));
        ResponseEntity<RestResponse> responseEntity = controller.getProductDataById(id);
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getBody() instanceof WSProduct);
        WSProduct actualResponse = (WSProduct) responseEntity.getBody();
        assertNotNull(actualResponse);
        assertUserDetails(expectedResponse, actualResponse);
    }

    private WSProduct generateWSUserDetails() {
        WSProduct details = new WSProduct();

        details.setExperienceDetails(RandomStringUtils.randomAlphabetic(10));
        details.setContent(RandomStringUtils.randomAlphabetic(10));
        details.setInventory(UUID.randomUUID()
                .toString());
        return details;
    }

    private void assertUserDetails(WSProduct expected, WSProduct actual) {
        assertEquals(expected.getInventory(), actual.getInventory());
        assertEquals(expected.getExperienceDetails(), actual.getExperienceDetails());
        assertEquals(expected.getContent(), actual.getContent());
        assertEquals(expected.getResult(), actual.getResult());

    }
}
