package com.kanchanJS.ecommerce.service;

import com.kanchanJS.ecommerce.dto.PurchaseResponse;
import com.kanchanJS.ecommerce.dto.Purchase;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

}
