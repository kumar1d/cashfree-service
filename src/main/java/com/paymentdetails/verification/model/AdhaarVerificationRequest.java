package com.paymentdetails.verification.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="adhar-verification")
public class AdhaarVerificationRequest {
@Id

private String id; 
private String adharNumber;
private String name;
public String getadharNumber() {

	return adharNumber;
}
public void setPanNumber(String adharNumber) {

	this.adharNumber = adharNumber;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}


}
