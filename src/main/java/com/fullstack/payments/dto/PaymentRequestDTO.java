package com.fullstack.payments.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PaymentRequestDTO {

    @NotNull(message = "El ID del pedido es obligatorio")
    private UUID orderId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID userId;

    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a 0")
    private BigDecimal total;

    /*
     * Para simular pago:
     * true  -> APPROVED
     * false -> REJECTED
     *
     * En una integración real, esto vendría desde Webpay, MercadoPago, etc.
     */
    private boolean approved = true;
}