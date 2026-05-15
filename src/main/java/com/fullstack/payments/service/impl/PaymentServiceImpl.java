package com.fullstack.payments.service.impl;

import com.fullstack.payments.dto.PaymentRequestDTO;
import com.fullstack.payments.model.Payment;
import com.fullstack.payments.model.PaymentStatus;
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

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment processPayment(PaymentRequestDTO request) {
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .total(request.getTotal())
                .status(request.isApproved() ? PaymentStatus.APPROVED : PaymentStatus.REJECTED)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        if (savedPayment.getStatus() == PaymentStatus.APPROVED) {
            System.out.println("PAYMENTS: Pago aprobado para pedido " + savedPayment.getOrderId());
        } else {
            System.out.println("PAYMENTS: Pago rechazado para pedido " + savedPayment.getOrderId());
        }

        return savedPayment;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pago no encontrado con ID: " + paymentId
                ));
    }

    @Override
    public List<Payment> getPaymentsByOrderId(UUID orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    @Override
    public Payment markRefundPending(UUID paymentId) {
        Payment payment = getPaymentById(paymentId);
        payment.setStatus(PaymentStatus.REFUND_PENDING);
        return paymentRepository.save(payment);
    }
}