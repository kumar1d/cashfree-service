package com.paymentdetails.verification.repository;


import com.paymentdetails.verification.model.PanVerification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface PanVerificationrepository extends MongoRepository<PanVerification,String> {

}
