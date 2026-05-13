package com.fullstack.payments.service;

import com.fullstack.payments.dto.PaymentRequest;
import com.fullstack.payments.model.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    Payment createPayment(PaymentRequest request);

    List<Payment> getAllPayments();

    Payment getPaymentById(UUID id);
}