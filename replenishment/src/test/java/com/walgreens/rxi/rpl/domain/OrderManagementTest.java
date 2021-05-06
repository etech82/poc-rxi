package com.walgreens.rxi.rpl.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.walgreens.rxi.rpl.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderManagementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderManagement.class);
        OrderManagement orderManagement1 = new OrderManagement();
        orderManagement1.setId("id1");
        OrderManagement orderManagement2 = new OrderManagement();
        orderManagement2.setId(orderManagement1.getId());
        assertThat(orderManagement1).isEqualTo(orderManagement2);
        orderManagement2.setId("id2");
        assertThat(orderManagement1).isNotEqualTo(orderManagement2);
        orderManagement1.setId(null);
        assertThat(orderManagement1).isNotEqualTo(orderManagement2);
    }
}
