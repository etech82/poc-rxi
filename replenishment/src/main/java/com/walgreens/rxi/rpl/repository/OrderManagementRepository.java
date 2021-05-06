package com.walgreens.rxi.rpl.repository;

import com.walgreens.rxi.rpl.domain.OrderManagement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the OrderManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderManagementRepository extends MongoRepository<OrderManagement, String> {}
