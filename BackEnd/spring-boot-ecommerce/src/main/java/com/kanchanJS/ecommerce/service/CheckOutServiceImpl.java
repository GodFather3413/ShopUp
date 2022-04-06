package com.kanchanJS.ecommerce.service;

import com.kanchanJS.ecommerce.dao.CustomerRepository;
import com.kanchanJS.ecommerce.dto.Purchase;
import com.kanchanJS.ecommerce.dto.PurchaseResponse;
import com.kanchanJS.ecommerce.entity.Customer;
import com.kanchanJS.ecommerce.entity.Order;
import com.kanchanJS.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckOutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    public CheckOutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4) Universal unique identifier   https://en.wikipedia.org/wiki/Universally_unique_identifier

        return UUID.randomUUID().toString();
    }
}
