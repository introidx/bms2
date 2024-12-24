package com.introidx.bms2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitiatePaymentDTO {
    private String paymentMethod;
    private String paymentGateway;
    private Long amount;
    private Long ticketId;
}