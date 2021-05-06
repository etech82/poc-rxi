package com.walgreens.rxi.rpl.service;

import com.walgreens.rxi.rpl.domain.OrderManagement;
import com.walgreens.rxi.rpl.repository.OrderManagementRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link OrderManagement}.
 */
@Service
public class OrderManagementService {

    private final Logger log = LoggerFactory.getLogger(OrderManagementService.class);

    private final OrderManagementRepository orderManagementRepository;

    public OrderManagementService(OrderManagementRepository orderManagementRepository) {
        this.orderManagementRepository = orderManagementRepository;
    }

    /**
     * Save a orderManagement.
     *
     * @param orderManagement the entity to save.
     * @return the persisted entity.
     */
    public OrderManagement save(OrderManagement orderManagement) {
        log.debug("Request to save OrderManagement : {}", orderManagement);
        return orderManagementRepository.save(orderManagement);
    }

    /**
     * Partially update a orderManagement.
     *
     * @param orderManagement the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrderManagement> partialUpdate(OrderManagement orderManagement) {
        log.debug("Request to partially update OrderManagement : {}", orderManagement);

        return orderManagementRepository
            .findById(orderManagement.getId())
            .map(
                existingOrderManagement -> {
                    if (orderManagement.getCode() != null) {
                        existingOrderManagement.setCode(orderManagement.getCode());
                    }
                    if (orderManagement.getLocationOrder() != null) {
                        existingOrderManagement.setLocationOrder(orderManagement.getLocationOrder());
                    }
                    if (orderManagement.getType() != null) {
                        existingOrderManagement.setType(orderManagement.getType());
                    }

                    return existingOrderManagement;
                }
            )
            .map(orderManagementRepository::save);
    }

    /**
     * Get all the orderManagements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<OrderManagement> findAll(Pageable pageable) {
        log.debug("Request to get all OrderManagements");
        return orderManagementRepository.findAll(pageable);
    }

    /**
     * Get one orderManagement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<OrderManagement> findOne(String id) {
        log.debug("Request to get OrderManagement : {}", id);
        return orderManagementRepository.findById(id);
    }

    /**
     * Delete the orderManagement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete OrderManagement : {}", id);
        orderManagementRepository.deleteById(id);
    }
}
