package uz.pdp.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route(p -> p
                        .path("/api/auth/v1/**")
                        .filters(f -> f.addRequestHeader("Ketmon", "Otabek"))
                        .uri("lb://AUTH-SERVICE"))
                .route(p -> p
                        .path("/api/order/v1/**")
                        .filters(f -> f.addRequestHeader("Bolta", "Shafaq"))
                        .uri("lb://ORDER-PARENT-SERVICE"))
                .build();
    }
}
