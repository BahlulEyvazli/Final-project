package com.example.bazaarstore.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeService {

    private final String apiKey;

    public Charge createCharge(Long amount, String currency, String source, String description) throws StripeException {
        ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .setSource(source)
                .setDescription(description)
                .build();
        return Charge.create(chargeParams);
    }

    public Customer createCustomer(String email, String name) throws StripeException {
        CustomerCreateParams customerParams = CustomerCreateParams.builder()
                .setEmail(email)
                .setName(name)
                .build();
        return Customer.create(customerParams);
    }

    public Customer retrieveCustomer(String customerId) throws StripeException{
        return Customer.retrieve(customerId);
    }


}
