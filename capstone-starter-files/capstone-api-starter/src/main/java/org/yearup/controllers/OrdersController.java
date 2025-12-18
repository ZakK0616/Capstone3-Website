package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.OrderDao;
import org.yearup.data.OrderLineItemDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class OrdersController
{
    private ShoppingCartDao shoppingCartDao;
    private OrderDao orderDao;
    private UserDao userDao;
    private OrderLineItemDao orderLineItemDao;

    @Autowired
    public OrdersController (ShoppingCartDao shoppingCartDao,
                             OrderLineItemDao orderLineItemDao,
                             OrderDao orderDao,
                             UserDao userDao)
    {
        this.shoppingCartDao = shoppingCartDao;
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.orderLineItemDao = orderLineItemDao;
    }

    @PostMapping
    public Order checkout (Principal principal)
    {
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        int userId = user.getId();

        ShoppingCart cart = shoppingCartDao.getByUserId(userId);
        int orderId = orderDao.createOrder(userId, cart.getTotal());

        for (ShoppingCartItem item : cart.getItems().values())
        {
            orderLineItemDao.addLineItem(orderId, item.getProductId(), item.getQuantity(), item.getProduct().getPrice());
            shoppingCartDao.clearCart(userId);
            return new Order(orderId, userId, cart.getTotal());
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Checkout failed.");
        }
    }

}
