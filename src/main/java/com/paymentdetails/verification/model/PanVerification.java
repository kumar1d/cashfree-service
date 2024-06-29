package com.paymentdetails.verification.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="pan-verification")
public class PanVerification {
@Id

private String id; 
private String PanNumber;
private String name;
public String getPanNumber() {
	return PanNumber;
}
public void setPanNumber(String panNumber) {
	PanNumber = panNumber;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}


}
