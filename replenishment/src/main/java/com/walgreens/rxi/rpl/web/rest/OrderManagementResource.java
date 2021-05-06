package com.walgreens.rxi.rpl.web.rest;

import com.walgreens.rxi.rpl.domain.OrderManagement;
import com.walgreens.rxi.rpl.repository.OrderManagementRepository;
import com.walgreens.rxi.rpl.service.OrderManagementService;
import com.walgreens.rxi.rpl.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.walgreens.rxi.rpl.domain.OrderManagement}.
 */
@RestController
@RequestMapping("/api")
public class OrderManagementResource {

    private final Logger log = LoggerFactory.getLogger(OrderManagementResource.class);

    private static final String ENTITY_NAME = "replenishmentOrderManagement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderManagementService orderManagementService;

    private final OrderManagementRepository orderManagementRepository;

    public OrderManagementResource(OrderManagementService orderManagementService, OrderManagementRepository orderManagementRepository) {
        this.orderManagementService = orderManagementService;
        this.orderManagementRepository = orderManagementRepository;
    }

    /**
     * {@code POST  /order-managements} : Create a new orderManagement.
     *
     * @param orderManagement the orderManagement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderManagement, or with status {@code 400 (Bad Request)} if the orderManagement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-managements")
    public ResponseEntity<OrderManagement> createOrderManagement(@Valid @RequestBody OrderManagement orderManagement)
        throws URISyntaxException {
        log.debug("REST request to save OrderManagement : {}", orderManagement);
        if (orderManagement.getId() != null) {
            throw new BadRequestAlertException("A new orderManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderManagement result = orderManagementService.save(orderManagement);
        return ResponseEntity
            .created(new URI("/api/order-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /order-managements/:id} : Updates an existing orderManagement.
     *
     * @param id the id of the orderManagement to save.
     * @param orderManagement the orderManagement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderManagement,
     * or with status {@code 400 (Bad Request)} if the orderManagement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderManagement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-managements/{id}")
    public ResponseEntity<OrderManagement> updateOrderManagement(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody OrderManagement orderManagement
    ) throws URISyntaxException {
        log.debug("REST request to update OrderManagement : {}, {}", id, orderManagement);
        if (orderManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderManagement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderManagementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderManagement result = orderManagementService.save(orderManagement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderManagement.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-managements/:id} : Partial updates given fields of an existing orderManagement, field will ignore if it is null
     *
     * @param id the id of the orderManagement to save.
     * @param orderManagement the orderManagement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderManagement,
     * or with status {@code 400 (Bad Request)} if the orderManagement is not valid,
     * or with status {@code 404 (Not Found)} if the orderManagement is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderManagement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-managements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrderManagement> partialUpdateOrderManagement(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody OrderManagement orderManagement
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderManagement partially : {}, {}", id, orderManagement);
        if (orderManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderManagement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderManagementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderManagement> result = orderManagementService.partialUpdate(orderManagement);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderManagement.getId())
        );
    }

    /**
     * {@code GET  /order-managements} : get all the orderManagements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderManagements in body.
     */
    @GetMapping("/order-managements")
    public ResponseEntity<List<OrderManagement>> getAllOrderManagements(Pageable pageable) {
        log.debug("REST request to get a page of OrderManagements");
        Page<OrderManagement> page = orderManagementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-managements/:id} : get the "id" orderManagement.
     *
     * @param id the id of the orderManagement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderManagement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-managements/{id}")
    public ResponseEntity<OrderManagement> getOrderManagement(@PathVariable String id) {
        log.debug("REST request to get OrderManagement : {}", id);
        Optional<OrderManagement> orderManagement = orderManagementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderManagement);
    }

    /**
     * {@code DELETE  /order-managements/:id} : delete the "id" orderManagement.
     *
     * @param id the id of the orderManagement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-managements/{id}")
    public ResponseEntity<Void> deleteOrderManagement(@PathVariable String id) {
        log.debug("REST request to delete OrderManagement : {}", id);
        orderManagementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
