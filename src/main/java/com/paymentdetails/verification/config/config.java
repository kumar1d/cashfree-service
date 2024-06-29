package com.paymentdetails.verification.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class config {

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder()
				.baseUrl("https://sandbox.cashfree.com")
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
				.defaultHeader("Content-Type", "application/json")
				.defaultHeader("Accept", "application/json")
				.clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true).resolver(DefaultAddressResolverGroup.INSTANCE)));
	}
}

