package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.cart.CartDTO;
import com.example.bazaarstore.dto.cart.CartItemDTO;
import com.example.bazaarstore.dto.stripe.CheckItemDTO;

import com.example.bazaarstore.model.entity.Order;
import com.example.bazaarstore.model.entity.OrderItem;
import com.example.bazaarstore.model.entity.User;
import com.example.bazaarstore.repository.OrderItemRepository;
import com.example.bazaarstore.repository.OrderRepository;
import com.example.bazaarstore.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public OrderService(CartService cartService, OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository, JwtService jwtService,
                        UserRepository userRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Value("${STRIPE_SECRET_KEY}")
    private String stripeKey;

    @Value("${BASE_URL}")
    private String baseUrl;

    public Session createSession(List<CheckItemDTO> checkItemDTOList) throws StripeException {

        String successUrl = baseUrl + "payment/success";
        String failureUrl = baseUrl + "payment/failed";

        Stripe.apiKey=stripeKey;

        List<SessionCreateParams.LineItem> sessionList = new ArrayList<>();

        for (CheckItemDTO checkItemDTO : checkItemDTOList){
            sessionList.add(createSessionLineItem(checkItemDTO));
        }

        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureUrl)
                .setSuccessUrl(successUrl)
                .addAllLineItem(sessionList).build();

        return Session.create(sessionCreateParams);
    }


    private SessionCreateParams.LineItem createSessionLineItem(CheckItemDTO checkItemDTO){
        return SessionCreateParams.LineItem.builder().setPriceData(createPriceData(checkItemDTO))
                .setQuantity(checkItemDTO.getQuantity()).build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CheckItemDTO checkItemDTO){
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long)checkItemDTO.getPrice()*100)
                .setProductData(
                        SessionCreateParams.LineItem
                                .PriceData.ProductData.builder().setName(checkItemDTO.getProductName()).build()
                ).build();

    }

    @Transactional
    public void placeOrder(String sessionId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        CartDTO cartDto = cartService.listCartItems();
        List<CartItemDTO> cartItemDtoList = cartDto.getCartItems();
        Order newOrder = Order.builder().sessionId(sessionId).user(user).totalPrice(cartDto.getTotalCost()).build();
        orderRepository.save(newOrder);

        for (CartItemDTO cartItemDto : cartItemDtoList) {
            OrderItem orderItem = OrderItem.builder().price(cartItemDto.getProduct().getUnitPrice())
                            .product(cartItemDto.getProduct()).quantity(cartItemDto.getQuantity()).order(newOrder).build();
            orderItemRepository.save(orderItem);
        }

        cartService.deleteUserCartItems(user);
    }

    public List<Order> listOrders() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
    }


    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }
}
