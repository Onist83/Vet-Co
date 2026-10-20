package com.onist.animal.feign;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;

// Configuration class for Feign clients. It defines a RequestInterceptor bean that forwards the Authorization header from incoming requests to outgoing Feign client requests
// Classe de configuration pour les clients Feign. Elle définit un bean RequestInterceptor qui transfère l'en-tête Authorization des requêtes entrantes vers les requêtes sortantes du client Feign
@Configuration
public class ConfigurationFeign {

    // Interceptor to forward the Authorization header from incoming requests to
    // outgoing Feign client requests
    // Intercepteur pour transférer l'en-tête Authorization des requêtes entrantes
    // vers les requêtes sortantes du client Feign
    @Bean
    public RequestInterceptor forwardAuthorizationHeaderInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String authorizationHeader = request.getHeader("Authorization");
                if (authorizationHeader != null) {
                    requestTemplate.header("Authorization", authorizationHeader);
                }
            }
        };
    }
}
