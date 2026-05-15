package com.fullstack.payments.dto;

import com.fullstack.payments.model.Payment;
import com.fullstack.payments.model.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class PaymentResponseDTO {

    private UUID paymentId;
    private UUID orderId;
    private UUID userId;
    private BigDecimal total;
    private PaymentStatus status;
    private boolean approved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PaymentResponseDTO fromEntity(Payment payment) {
        boolean approved = payment.getStatus() == PaymentStatus.APPROVED;

        return PaymentResponseDTO.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .total(payment.getTotal())
                .status(payment.getStatus())
                .approved(approved)
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
