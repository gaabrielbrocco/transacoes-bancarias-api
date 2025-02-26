package com.java.transacoes_api.global.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.logs.Logger;
import io.opentelemetry.api.logs.LoggerProvider;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {

    @Value("${otel.trace.endpoint}")
    private String traceEndPoint;

    private SpanExporter spanExporter() {
        return OtlpHttpSpanExporter
                .builder()
                .setEndpoint(traceEndPoint)
                .build();
    }

    private SpanProcessor spanProcessor() {
        return SimpleSpanProcessor
                .builder(spanExporter())
                .build();
    }

    private SdkTracerProvider sdkTracerProvider() {
        Resource resource = Resource.builder().put("service.name", "app").build();
        return SdkTracerProvider
                .builder()
                .setResource(resource)
                .addSpanProcessor(spanProcessor())
                .build();
    }

    @Bean
    public OpenTelemetry openTelemetry() {
        return OpenTelemetrySdk.builder().build();
    }

    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer("app");
    }


    @Bean
    public LoggerProvider loggerProvider() {
        return SdkLoggerProvider.builder().build();
    }

    @Bean
    public Logger logger(LoggerProvider loggerProvider) {
        return loggerProvider.get("app");
    }
}
