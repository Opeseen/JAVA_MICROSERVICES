package com.ubaclone.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

  @Bean
  public RouteLocator ubaCloneRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
    return routeLocatorBuilder.routes()
        .route(p -> p
            .path("/ubaclone/accounts/**")
            .filters((f -> f.rewritePath("/ubaclone/accounts/(?<segment>.*)","/${segment}")
                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                .retry(retryConfig -> retryConfig.setRetries(3)
                    .setMethods(HttpMethod.GET)
                    .setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true))
                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                    .setFallbackUri("forward:/contactSupport"))))
            .uri("lb://ACCOUNTS")
        )
        .route(p -> p
            .path("/ubaclone/eservice/**")
            .filters((f -> f.rewritePath("/ubaclone/eservice/(?<segment>.*)","/${segment}")
                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())))
            .uri("lb://ESERVICE")

        ).build();
  }

  // change the default timeout configuration of circuit breaker
  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
    return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
        .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build()).build());
  }

}
