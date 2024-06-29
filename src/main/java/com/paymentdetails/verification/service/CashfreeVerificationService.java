package com.paymentdetails.verification.service;



import com.paymentdetails.verification.model.*;
import com.paymentdetails.verification.repository.AdhaarVerificationRepository;
import com.paymentdetails.verification.repository.PanVerificationrepository;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;


@Service
@Slf4j
public class CashfreeVerificationService {

	


	public  WebClient webClient;
	public  AdhaarVerificationRepository adharAdhaarVerificationRepository;
	public  PanVerificationrepository panVerificationrepository;


	public CashfreeVerificationService(WebClient.Builder webClientBuilder, AdhaarVerificationRepository adharAdhaarVerificationRepository, PanVerificationrepository panVerificationrepository) {
		this.webClient= webClientBuilder.baseUrl(baseurl).build();
		this.adharAdhaarVerificationRepository=adharAdhaarVerificationRepository;
		this.panVerificationrepository=panVerificationrepository;

	}
	
	
	public VerificationResponse VerifyAdhar(AdhaarVerificationRequest request) {
		String url=UriComponentsBuilder.fromHttpUrl(baseurl).pathSegment("verification","gstin").toUriString();
		log.debug("Request URL: {}", url);
		log.debug("Request Body: {}", request);

		return webClient.post()
				.uri(url)
				.headers(headers -> {
					headers.setBasicAuth(ClientId, ClientSecret);
					headers.add("x-client-id", ClientId);
					headers.add("x-client-secret", ClientSecret);
					headers.add("x-cf-signature", generateEncryptedSignature());
					headers.setContentType(MediaType.APPLICATION_JSON);
					headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.body(Mono.just(request), AdhaarVerificationRequest.class)
				.retrieve()
				.bodyToMono(VerificationResponse.class)
				.doOnNext(response -> log.debug("Response Body: {}", response))
				.block();  // Use block() for synchronous call
	}


	
	public VerificationResponse verifiPan(PanVerification request) {
		String url=UriComponentsBuilder.fromHttpUrl(baseurl).pathSegment("verification","pan").toUriString();

		log.debug("Request URL: {}", url);
		log.debug("Request Body: {}", request);

		return webClient.post()
				.uri(url)
				.headers(headers -> {
					headers.setBasicAuth(ClientId, ClientSecret);
					headers.add("x-client-id", ClientId);
					headers.add("x-client-secret", ClientSecret);
					headers.add("x-cf-signature",generateEncryptedSignature());
					headers.setContentType(MediaType.APPLICATION_JSON);
					headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.body(Mono.just(request), PanVerification.class)
				.retrieve()
				.bodyToMono(VerificationResponse.class)
				.doOnNext(response -> log.debug("Response Body: {}", response))
				.block();  // Use block() for synchronous call

	}
	public GSTINVerificationResponse verifyGstin(GSTINVerificationRequest request) {

		log.debug("Request URL: {}", URL_GSTIN);
		log.debug("Request Body: {}", request);

		return webClient.post()
				.uri(URL_GSTIN)
				.headers(headers -> {

					headers.add("x-client-id", ClientId);
					headers.add("x-client-secret", ClientSecret);
					headers.add("x-cf-signature", generateEncryptedSignature());
					headers.setContentType(MediaType.APPLICATION_JSON);
					headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.bodyValue( request)
				.retrieve()
				.bodyToMono(GSTINVerificationResponse.class)
				.doOnNext(response -> log.debug("Response Body: {}", response))
				.block();
	}


private  String generateEncryptedSignature() {
	String clientIdWithEpochTimestamp = ClientId+"."+Instant.now().getEpochSecond();
	String encrytedSignature = "";
	try {
		byte[] keyBytes = Files
				.readAllBytes(new File("src/main/resources/accountId_596149_public_key.pem").toPath()); // Absolute Path to be replaced
		String publicKeyContent = new String(keyBytes);
		publicKeyContent = publicKeyContent.replaceAll("[\\t\\n\\r]", "")
				.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
		KeyFactory kf = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(
				Base64.getDecoder().decode(publicKeyContent));
		RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
		final Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		encrytedSignature = Base64.getEncoder().encodeToString(cipher.doFinal(clientIdWithEpochTimestamp.getBytes()));
	} catch (Exception e) {
		e.printStackTrace();
	}
	return encrytedSignature;
}


	
	

}
