package com.example.bazaarstore.controller;

import com.example.bazaarstore.dto.payment.PaymentDTO;
import com.example.bazaarstore.dto.stripe.CheckItemDTO;
import com.example.bazaarstore.dto.stripe.StripeResponse;
import com.example.bazaarstore.model.entity.Order;
import com.example.bazaarstore.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Must be secure
@RestController
@RequestMapping("/bazaar/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/check-session")
    public ResponseEntity<?> checkOutList(@RequestBody List<CheckItemDTO> checkItemDTOS) throws StripeException {
        Session session = orderService.createSession(checkItemDTOS);
        StripeResponse stripeResponse = StripeResponse.builder().sessionId(session.getId()).build();
        log.info("SESSION :" + stripeResponse);
        return ResponseEntity.ok(stripeResponse);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllOrders()   {

        List<Order> orderDtoList = orderService.listOrders();

        return ResponseEntity.ok(orderDtoList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> placeOrder(@RequestParam("sessionId") String sessionId){
       orderService.placeOrder(sessionId);
       return ResponseEntity.ok("Order successfully");
    }


    @PostMapping("/payment")
    public ResponseEntity<?> makePayment(@RequestBody PaymentDTO paymentDTO){
        return ResponseEntity.ok(orderService.makePayment(paymentDTO));
    }

}
