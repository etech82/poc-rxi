package com.walgreens.rxi.rpl.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.walgreens.rxi.rpl.IntegrationTest;
import com.walgreens.rxi.rpl.domain.OrderManagement;
import com.walgreens.rxi.rpl.domain.enumeration.OrderManagerType;
import com.walgreens.rxi.rpl.repository.OrderManagementRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link OrderManagementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderManagementResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_LOCATION_ORDER = 1L;
    private static final Long UPDATED_LOCATION_ORDER = 2L;

    private static final OrderManagerType DEFAULT_TYPE = OrderManagerType.MANUAL;
    private static final OrderManagerType UPDATED_TYPE = OrderManagerType.AUTOMATION;

    private static final String ENTITY_API_URL = "/api/order-managements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private OrderManagementRepository orderManagementRepository;

    @Autowired
    private MockMvc restOrderManagementMockMvc;

    private OrderManagement orderManagement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderManagement createEntity() {
        OrderManagement orderManagement = new OrderManagement().code(DEFAULT_CODE).locationOrder(DEFAULT_LOCATION_ORDER).type(DEFAULT_TYPE);
        return orderManagement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderManagement createUpdatedEntity() {
        OrderManagement orderManagement = new OrderManagement().code(UPDATED_CODE).locationOrder(UPDATED_LOCATION_ORDER).type(UPDATED_TYPE);
        return orderManagement;
    }

    @BeforeEach
    public void initTest() {
        orderManagementRepository.deleteAll();
        orderManagement = createEntity();
    }

    @Test
    void createOrderManagement() throws Exception {
        int databaseSizeBeforeCreate = orderManagementRepository.findAll().size();
        // Create the OrderManagement
        restOrderManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isCreated());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeCreate + 1);
        OrderManagement testOrderManagement = orderManagementList.get(orderManagementList.size() - 1);
        assertThat(testOrderManagement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOrderManagement.getLocationOrder()).isEqualTo(DEFAULT_LOCATION_ORDER);
        assertThat(testOrderManagement.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createOrderManagementWithExistingId() throws Exception {
        // Create the OrderManagement with an existing ID
        orderManagement.setId("existing_id");

        int databaseSizeBeforeCreate = orderManagementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderManagementRepository.findAll().size();
        // set the field null
        orderManagement.setCode(null);

        // Create the OrderManagement, which fails.

        restOrderManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isBadRequest());

        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLocationOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderManagementRepository.findAll().size();
        // set the field null
        orderManagement.setLocationOrder(null);

        // Create the OrderManagement, which fails.

        restOrderManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isBadRequest());

        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderManagementRepository.findAll().size();
        // set the field null
        orderManagement.setType(null);

        // Create the OrderManagement, which fails.

        restOrderManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isBadRequest());

        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllOrderManagements() throws Exception {
        // Initialize the database
        orderManagementRepository.save(orderManagement);

        // Get all the orderManagementList
        restOrderManagementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderManagement.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].locationOrder").value(hasItem(DEFAULT_LOCATION_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    void getOrderManagement() throws Exception {
        // Initialize the database
        orderManagementRepository.save(orderManagement);

        // Get the orderManagement
        restOrderManagementMockMvc
            .perform(get(ENTITY_API_URL_ID, orderManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderManagement.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.locationOrder").value(DEFAULT_LOCATION_ORDER.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    void getNonExistingOrderManagement() throws Exception {
        // Get the orderManagement
        restOrderManagementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewOrderManagement() throws Exception {
        // Initialize the database
        orderManagementRepository.save(orderManagement);

        int databaseSizeBeforeUpdate = orderManagementRepository.findAll().size();

        // Update the orderManagement
        OrderManagement updatedOrderManagement = orderManagementRepository.findById(orderManagement.getId()).get();
        updatedOrderManagement.code(UPDATED_CODE).locationOrder(UPDATED_LOCATION_ORDER).type(UPDATED_TYPE);

        restOrderManagementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderManagement.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrderManagement))
            )
            .andExpect(status().isOk());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeUpdate);
        OrderManagement testOrderManagement = orderManagementList.get(orderManagementList.size() - 1);
        assertThat(testOrderManagement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOrderManagement.getLocationOrder()).isEqualTo(UPDATED_LOCATION_ORDER);
        assertThat(testOrderManagement.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingOrderManagement() throws Exception {
        int databaseSizeBeforeUpdate = orderManagementRepository.findAll().size();
        orderManagement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderManagementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderManagement.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOrderManagement() throws Exception {
        int databaseSizeBeforeUpdate = orderManagementRepository.findAll().size();
        orderManagement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderManagementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOrderManagement() throws Exception {
        int databaseSizeBeforeUpdate = orderManagementRepository.findAll().size();
        orderManagement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderManagementMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOrderManagementWithPatch() throws Exception {
        // Initialize the database
        orderManagementRepository.save(orderManagement);

        int databaseSizeBeforeUpdate = orderManagementRepository.findAll().size();

        // Update the orderManagement using partial update
        OrderManagement partialUpdatedOrderManagement = new OrderManagement();
        partialUpdatedOrderManagement.setId(orderManagement.getId());

        restOrderManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderManagement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderManagement))
            )
            .andExpect(status().isOk());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeUpdate);
        OrderManagement testOrderManagement = orderManagementList.get(orderManagementList.size() - 1);
        assertThat(testOrderManagement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOrderManagement.getLocationOrder()).isEqualTo(DEFAULT_LOCATION_ORDER);
        assertThat(testOrderManagement.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateOrderManagementWithPatch() throws Exception {
        // Initialize the database
        orderManagementRepository.save(orderManagement);

        int databaseSizeBeforeUpdate = orderManagementRepository.findAll().size();

        // Update the orderManagement using partial update
        OrderManagement partialUpdatedOrderManagement = new OrderManagement();
        partialUpdatedOrderManagement.setId(orderManagement.getId());

        partialUpdatedOrderManagement.code(UPDATED_CODE).locationOrder(UPDATED_LOCATION_ORDER).type(UPDATED_TYPE);

        restOrderManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderManagement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderManagement))
            )
            .andExpect(status().isOk());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeUpdate);
        OrderManagement testOrderManagement = orderManagementList.get(orderManagementList.size() - 1);
        assertThat(testOrderManagement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOrderManagement.getLocationOrder()).isEqualTo(UPDATED_LOCATION_ORDER);
        assertThat(testOrderManagement.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingOrderManagement() throws Exception {
        int databaseSizeBeforeUpdate = orderManagementRepository.findAll().size();
        orderManagement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderManagement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOrderManagement() throws Exception {
        int databaseSizeBeforeUpdate = orderManagementRepository.findAll().size();
        orderManagement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOrderManagement() throws Exception {
        int databaseSizeBeforeUpdate = orderManagementRepository.findAll().size();
        orderManagement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderManagementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderManagement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderManagement in the database
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOrderManagement() throws Exception {
        // Initialize the database
        orderManagementRepository.save(orderManagement);

        int databaseSizeBeforeDelete = orderManagementRepository.findAll().size();

        // Delete the orderManagement
        restOrderManagementMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderManagement.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderManagement> orderManagementList = orderManagementRepository.findAll();
        assertThat(orderManagementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
