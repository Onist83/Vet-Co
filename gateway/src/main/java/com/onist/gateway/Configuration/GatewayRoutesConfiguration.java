package com.onist.gateway.Configuration;

@Configuration
public class GatewayRoutesConfiguration {

    @Bean
    public RouterFunction<ServerResponse> animalRoute() {
        return GatewayRouterFunction.route("animal")
        .route(GatewayRequestPredicates.path("api/v1/animal/**"), HandlerFunction.http("http://animal:8010"))
        .build();
    }

    @Bean 
    public RouterFunction<ServerResponse> userRoute() {
        return GatewayRouterFunction.route("user")
        .route(GatewayRequestPredicates.path("api/v1/user/**"), HandlerFunction.http("http://user:8020"))
        .build();
    }

    @Bean
    public RouterFunction<ServerResponse> authRoute() {
        return GatewayRouterFunction.route("auth")
        .route(GatewayRequestPredicates.path("api/v1/auth/**"), HandlerFunction.http("http://auth:8030"))
        .build();
    }

    @Bean 
    public RouterFunction<ServerResponse> commentaryRoute() {
        return GatewayRouterFunction.route("commentary")
        .route(GatewayRequestPredicates.path("api/v1/commentary/**"), HandlerFunction.http("http://commentary:8060"))
        .build();
    }

    @Bean
    public RouterFunction<ServerResponse> appointmentRoute() {
        return GatewayRouterFunction.route("appointment")
        .route(GatewayRequestPredicates.path("api/v1/appointment/**"), HandlerFunction.http("http://appointment:8070"))
        .build();
    }

}
