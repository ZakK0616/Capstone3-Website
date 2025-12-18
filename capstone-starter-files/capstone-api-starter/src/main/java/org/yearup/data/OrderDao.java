package org.yearup.data;

import java.math.BigDecimal;

public interface OrderDao {
    int createOrder(int userId, BigDecimal total);
}
