package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.stripe.CheckItemDTO;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

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
}
