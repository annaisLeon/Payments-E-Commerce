package com.fullstack.payments.controller;

import com.fullstack.payments.dto.PaymentRequestDTO;
import com.fullstack.payments.dto.PaymentResponseDTO;
import com.fullstack.payments.model.Payment;
import com.fullstack.payments.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @Valid @RequestBody PaymentRequestDTO request
    ) {
        Payment payment = paymentService.processPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PaymentResponseDTO.fromEntity(payment));
    }

    @GetMapping
    public ResponseEntity<?> getAllPayments() {
        return ResponseEntity.ok(
                paymentService.getAllPayments()
                        .stream()
                        .map(PaymentResponseDTO::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(
            @PathVariable UUID paymentId
    ) {
        Payment payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(PaymentResponseDTO.fromEntity(payment));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getPaymentsByOrderId(
            @PathVariable UUID orderId
    ) {
        return ResponseEntity.ok(
                paymentService.getPaymentsByOrderId(orderId)
                        .stream()
                        .map(PaymentResponseDTO::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    @PatchMapping("/{paymentId}/refund-pending")
    public ResponseEntity<PaymentResponseDTO> markRefundPending(
            @PathVariable UUID paymentId
    ) {
        Payment payment = paymentService.markRefundPending(paymentId);
        return ResponseEntity.ok(PaymentResponseDTO.fromEntity(payment));
    }
}