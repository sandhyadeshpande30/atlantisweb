package com.sdpaymentgateway.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdpaymentgateway.model.PaymentTransaction; 

@Repository
public interface TransactionRepository extends JpaRepository<PaymentTransaction, Integer>{

}
