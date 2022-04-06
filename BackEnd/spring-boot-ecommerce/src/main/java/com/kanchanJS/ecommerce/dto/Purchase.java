package com.kanchanJS.ecommerce.dto;

import com.kanchanJS.ecommerce.entity.Order;
import com.kanchanJS.ecommerce.entity.Address;
import com.kanchanJS.ecommerce.entity.Customer;
import com.kanchanJS.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
