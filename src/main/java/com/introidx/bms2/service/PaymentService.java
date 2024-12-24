package com.introidx.bms2.service;

import com.introidx.bms2.dto.InitiatePaymentDTO;
import com.introidx.bms2.dto.UpdatePaymentOrderDTO;
import com.introidx.bms2.entity.PaymentOrder;
import com.introidx.bms2.entity.Ticket;
import com.introidx.bms2.enums.PaymentOrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final BookingService bookingService;

    private List<PaymentOrder> paymentOrders = new ArrayList<>();

    public PaymentOrder initiatePayment(InitiatePaymentDTO initiatePaymentDTO) {
        Ticket ticket = bookingService.getTicketById(initiatePaymentDTO.getTicketId());
        if (ticket != null) {
            PaymentOrder paymentOrder = PaymentOrder.builder()
                    .id((long) (paymentOrders.size() + 1))
                    .ticket_id(ticket.getId())
                    .event_id(ticket.getEvent_id())
                    .user_id(ticket.getUser_id())
                    .totalPrice(initiatePaymentDTO.getAmount())
                    .status(PaymentOrderStatus.PENDING_PG)
                    .paymentMethod(initiatePaymentDTO.getPaymentMethod())
                    .paymentGateway(initiatePaymentDTO.getPaymentGateway())
                    .build();
            paymentOrders.add(paymentOrder);
            return paymentOrder;
        }
        throw new RuntimeException("Ticket not found!");
    }

    public List<PaymentOrder> getAllPaymentOrders() {
        return paymentOrders;
    }

    public PaymentOrder updatePaymentOrder(UpdatePaymentOrderDTO updatePaymentOrderDTO) {
        PaymentOrder paymentOrder = getPaymentOrderById(updatePaymentOrderDTO.getPaymentOrderId());
        if (paymentOrder != null) {
            paymentOrder.setStatus(updatePaymentOrderDTO.getStatus());
            paymentOrder.setPgReferenceId(updatePaymentOrderDTO.getPgReferenceId());
            paymentOrder.setBankReferenceId(updatePaymentOrderDTO.getBankReferenceId());

            paymentOrders.removeIf(po -> po.getId().equals(paymentOrder.getId()));
            paymentOrders.add(paymentOrder);

            // Update Ticket status
            bookingService.updateTicketStatus(paymentOrder.getTicket_id(), updatePaymentOrderDTO.getStatus());
            return paymentOrder;
        }
        throw new RuntimeException("Payment Order not found!");
    }

    public PaymentOrder getPaymentOrderById(Long id) {
        return paymentOrders.stream()
                .filter(paymentOrder -> paymentOrder.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
