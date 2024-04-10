package com.datadoghq;

import static io.opentelemetry.api.trace.SpanKind.SERVER;
import static io.opentelemetry.context.Context.root;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.context.propagation.TextMapPropagator;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

@Path("/storage")
public class StorageResource {
  private final Tracer tracer = GlobalOpenTelemetry.getTracer("storage");

  private static final TextMapPropagator PROPAGATOR
      = GlobalOpenTelemetry.getPropagators().getTextMapPropagator();

  private static final TextMapGetter<HttpHeaders> GETTER
      = new TextMapGetter<>() {
    @Override
    public Iterable<String> keys(HttpHeaders httpHeaders) {
      return httpHeaders.getRequestHeaders().keySet();
    }

    @Override
    public String get(HttpHeaders headers, String key) {
      return headers == null ? null : headers.getHeaderString(key);
    }
  };

  @GET()
  @Path("/{item}")
  @Produces(APPLICATION_JSON)
  public String checkItem(@PathParam("item") String item,
                          @Context HttpHeaders headers) {
    var context = PROPAGATOR.extract(root(), headers, GETTER);
    Span span = tracer.spanBuilder("checkItem")
        .setParent(context)
        .setSpanKind(SERVER)
        .startSpan();
    try (Scope scope = span.makeCurrent()) {
      int count = getCount(item);
      return "{\"name\": \"" + item + "\", \"count\": " + count + "}";
    } finally {
      span.end();
    }
  }

  public int getCount(String item) {
    Span span = tracer.spanBuilder("getCount")
        .setAttribute("item", item)
        .startSpan();
    // Simulate store computation
    try {
      Thread.sleep((long) (Math.random() * 100 + 150));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    span.end();
    return (int) (Math.random() * 150);
  }
}
