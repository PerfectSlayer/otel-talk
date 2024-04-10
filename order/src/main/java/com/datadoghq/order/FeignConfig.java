package com.datadoghq.order;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.context.propagation.TextMapSetter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
  // Header setter/
  private static final TextMapSetter<RequestTemplate> setter = (template, key, value) -> template.header(key, value);

  @Bean
  public RequestInterceptor requestInterceptor(OpenTelemetry openTelemetry) {
    // Tracing context propagator
    TextMapPropagator propagator = openTelemetry.getPropagators().getTextMapPropagator();
    // Inject headers
    return requestTemplate -> propagator.inject(Context.current(), requestTemplate, setter
    );
  }
}