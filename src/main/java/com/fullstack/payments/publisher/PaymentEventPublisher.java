package com.fullstack.payments.publisher;

import com.fullstack.payments.event.PaymentApprovedEvent;
import com.fullstack.payments.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentEventPublisher {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${orders.observer.url}")
    private String ordersObserverUrl;

    public void publishPaymentApproved(Payment payment) {
        try {
            PaymentApprovedEvent event = new PaymentApprovedEvent(
                    payment.getId(),
                    payment.getUserId(),
                    payment.getProductId(),
                    payment.getQuantity(),
                    payment.getTotal(),
                    payment.getStatus().name()
            );

            restTemplate.postForObject(
                    ordersObserverUrl,
                    event,
                    Void.class
            );

            System.out.println("PAYMENTS: Evento PaymentApproved enviado a Orders");
            System.out.println("PAYMENTS: Pago aprobado ID: " + payment.getId());

        } catch (RestClientException e) {
            System.out.println("PAYMENTS: No se pudo enviar el evento a Orders");
            System.out.println("PAYMENTS: Error: " + e.getMessage());
        }
    }
}