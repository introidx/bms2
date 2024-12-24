package com.introidx.bms2.controller;

import com.introidx.bms2.dto.InitiatePaymentDTO;
import com.introidx.bms2.dto.UpdatePaymentOrderDTO;
import com.introidx.bms2.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiatePayment(@RequestBody InitiatePaymentDTO request) {
        return ResponseEntity.ok(paymentService.initiatePayment(request));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPaymentOrders());
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePayment(@RequestBody UpdatePaymentOrderDTO request) {
        return ResponseEntity.ok(paymentService.updatePaymentOrder(request));
    }
}
