package com.walgreens.rxi.rpl.domain;

import com.walgreens.rxi.rpl.domain.enumeration.OrderManagerType;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A OrderManagement.
 */
@Document(collection = "order_management")
public class OrderManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("code")
    private String code;

    @NotNull
    @Field("location_order")
    private Long locationOrder;

    @NotNull
    @Field("type")
    private OrderManagerType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderManagement id(String id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public OrderManagement code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getLocationOrder() {
        return this.locationOrder;
    }

    public OrderManagement locationOrder(Long locationOrder) {
        this.locationOrder = locationOrder;
        return this;
    }

    public void setLocationOrder(Long locationOrder) {
        this.locationOrder = locationOrder;
    }

    public OrderManagerType getType() {
        return this.type;
    }

    public OrderManagement type(OrderManagerType type) {
        this.type = type;
        return this;
    }

    public void setType(OrderManagerType type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderManagement)) {
            return false;
        }
        return id != null && id.equals(((OrderManagement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderManagement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", locationOrder=" + getLocationOrder() +
            ", type='" + getType() + "'" +
            "}";
    }
}
