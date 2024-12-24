package com.introidx.bms2.entity;

import com.introidx.bms2.enums.PaymentOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrder {
    private Long id;
    private Long ticket_id;
    private Long user_id;
    private Long event_id;
    private Long totalPrice;
    private PaymentOrderStatus status;
    private String paymentGateway;
    private String paymentMethod;
    private String pgReferenceId;
    private String bankReferenceId;
}
