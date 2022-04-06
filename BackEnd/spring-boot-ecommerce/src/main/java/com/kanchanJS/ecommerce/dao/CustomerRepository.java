package com.kanchanJS.ecommerce.dao;

import com.kanchanJS.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {



}
