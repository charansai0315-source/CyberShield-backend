package com.cybershield.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class CyberShieldApplication {
    public static void main(String[] args) {
        SpringApplication.run(CyberShieldApplication.class, args);
    }

    @Bean
    RestClient mlRestClient(@Value("${cybershield.ml-service-url}") String baseUrl) {
        return RestClient.builder()
                .requestFactory(new SimpleClientHttpRequestFactory())
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    TomcatServletWebServerFactory tomcatServletWebServerFactory(
            @Value("${cybershield.tomcat.protocol:org.apache.coyote.http11.Http11Nio2Protocol}") String protocol) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setProtocol(protocol);
        return factory;
    }
}
