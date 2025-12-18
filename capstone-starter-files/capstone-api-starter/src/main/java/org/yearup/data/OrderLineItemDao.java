package org.yearup.data;

import java.math.BigDecimal;

public interface OrderLineItemDao {
    void addLineItem(int orderId, int productId, int quantity, BigDecimal price);
}
