package com.paymentdetails.verification.controller;

import com.paymentdetails.verification.model.*;
import com.paymentdetails.verification.service.CashfreeVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/verification")
public class Controller {
	@Autowired
	private CashfreeVerificationService cashfreeVerificationService;
	
	@PostMapping("/adhar")
	public VerificationResponse verifyaadhar(@RequestBody AdhaarVerificationRequest request) {
		return cashfreeVerificationService.VerifyAdhar(request);
	}
	@PostMapping("/pan")
	public VerificationResponse verifipan(@RequestBody PanVerification request ) {
		return cashfreeVerificationService.verifiPan(request);
	}
	@PostMapping("/gstin")
	public GSTINVerificationResponse verifyGstin(@RequestBody GSTINVerificationRequest request) {

		return cashfreeVerificationService.verifyGstin(request);
	}

}
