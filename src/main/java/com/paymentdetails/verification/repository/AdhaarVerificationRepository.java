package com.paymentdetails.verification.repository;

import com.paymentdetails.verification.model.AdhaarVerificationRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdhaarVerificationRepository extends MongoRepository<AdhaarVerificationRequest, String> {

}
