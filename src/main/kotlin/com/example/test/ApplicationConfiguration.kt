package com.example.test

import com.example.test.util.CustomObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.Charset
import java.util.*
import java.util.stream.IntStream

@Configuration
class ApplicationConfiguration {

    @Bean(name = ["jsonMapper"])
    @Primary
    fun jsonMapper(): ObjectMapper? {
        return CustomObjectMapper()
    }

    @Bean
    fun restTemplate(): RestTemplate? {
        val restTemplate = RestTemplate(getClientHttpRequestFactory()!!)
        val index = IntStream.range(
            0, restTemplate.messageConverters
                .size
        )
            .filter { i: Int ->
                restTemplate.messageConverters[i] is MappingJackson2HttpMessageConverter
            }
            .findFirst()
        if (index.isPresent) {
            restTemplate.messageConverters[index.asInt] = MappingJackson2HttpMessageConverter(jsonMapper()!!)
        }
        restTemplate.messageConverters
            .add(0, StringHttpMessageConverter(Charset.forName("UTF-8")))
        return restTemplate
    }

    private fun getClientHttpRequestFactory(): ClientHttpRequestFactory? {
        val clientHttpRequestFactory = HttpComponentsClientHttpRequestFactory()
        clientHttpRequestFactory.setConnectTimeout(30000)
        clientHttpRequestFactory.setReadTimeout(30000)
        return clientHttpRequestFactory
    }
}