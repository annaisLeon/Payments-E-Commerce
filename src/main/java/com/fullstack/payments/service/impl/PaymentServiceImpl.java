package com.fullstack.payments.service.impl;

import com.fullstack.payments.dto.PaymentRequest;
import com.fullstack.payments.model.Payment;
import com.fullstack.payments.model.PaymentStatus;
import com.fullstack.payments.publisher.PaymentEventPublisher;
import com.fullstack.payments.repository.PaymentRepository;
import com.fullstack.payments.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher paymentEventPublisher;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            PaymentEventPublisher paymentEventPublisher
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    @Override
    public Payment createPayment(PaymentRequest request) {
        Payment payment = Payment.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .total(request.getTotal())
                .status(request.isApproved() ? PaymentStatus.APPROVED : PaymentStatus.RECHAZED)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        if (savedPayment.getStatus() == PaymentStatus.APPROVED) {
            paymentEventPublisher.publishPaymentApproved(savedPayment);
        } else {
            System.out.println("PAYMENTS: Pago rechazado. No se crea pedido.");
        }

        return savedPayment;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pago no encontrado con ID: " + id
                ));
    }
}