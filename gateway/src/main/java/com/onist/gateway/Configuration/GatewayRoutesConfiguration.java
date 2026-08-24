package com.onist.gateway.configuration;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

// Gateway route configuration
// Each method defines a route that redirects
@Configuration
public class GatewayRoutesConfiguration {

    // Route to the Animal Service
    @Bean
    public RouterFunction<ServerResponse> animalRoute() {
    return GatewayRouterFunctions.route("animal")
        .route(GatewayRequestPredicates.path("api/v1/animal/**"), HandlerFunctions.http())
        .before(uri("http://animal:8010"))
        .build();
}
    // Route to the User Service
    @Bean 
    public RouterFunction<ServerResponse> userRoute() {
        return GatewayRouterFunctions.route("user")
        .route(GatewayRequestPredicates.path("api/v1/user/**"), HandlerFunctions.http())
        .before(uri("http://user:8020"))
        .build();
    }

    // Route to the Authentication Service
    @Bean
    public RouterFunction<ServerResponse> authRoute() {
        return GatewayRouterFunctions.route("auth")
        .route(GatewayRequestPredicates.path("api/v1/auth/**"), HandlerFunctions.http())
        .before(uri("http://user:8020"))
        .build();
    }

    // Route to the Notification Service
    @Bean 
    public RouterFunction<ServerResponse> commentaryRoute() {
        return GatewayRouterFunctions.route("commentary")
        .route(GatewayRequestPredicates.path("api/v1/commentary/**"), HandlerFunctions.http())
        .before(uri("http://commentary:8060"))
        .build();
    }


    // Route to the Appointment Service
    @Bean
    public RouterFunction<ServerResponse> appointmentRoute() {
        return GatewayRouterFunctions.route("appointment")
        .route(GatewayRequestPredicates.path("api/v1/appointment/**"), HandlerFunctions.http())
        .before(uri("http://appointment:8070"))
        .build();
    }

}
