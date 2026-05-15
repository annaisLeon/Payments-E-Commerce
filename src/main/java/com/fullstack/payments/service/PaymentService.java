package com.fullstack.payments.service;

import com.fullstack.payments.dto.PaymentRequestDTO;
import com.fullstack.payments.model.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    Payment processPayment(PaymentRequestDTO request);

    List<Payment> getAllPayments();

    Payment getPaymentById(UUID paymentId);

    List<Payment> getPaymentsByOrderId(UUID orderId);

    Payment markRefundPending(UUID paymentId);
}