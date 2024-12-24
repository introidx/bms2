package com.introidx.bms2.dto;

import com.introidx.bms2.enums.PaymentOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentOrderDTO {
    private Long paymentOrderId;
    private PaymentOrderStatus status;
    private String pgReferenceId;
    private String bankReferenceId;
}

