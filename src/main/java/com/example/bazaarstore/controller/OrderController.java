package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.stripe.CheckItemDTO;
import com.example.bazaarstore.dto.stripe.StripeResponse;
import com.example.bazaarstore.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Must be secure
@RestController
@RequestMapping("/bazaar/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/check-session")
    public ResponseEntity<?> checkOutList(@RequestBody List<CheckItemDTO> checkItemDTOS) throws StripeException {
        Session session = orderService.createSession(checkItemDTOS);
        StripeResponse stripeResponse = StripeResponse.builder().sessionId(session.getId()).build();
        return ResponseEntity.ok(stripeResponse);
    }
}
