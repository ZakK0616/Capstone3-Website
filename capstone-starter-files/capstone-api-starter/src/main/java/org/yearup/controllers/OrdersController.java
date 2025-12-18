package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;

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

    @Autowired
    public OrdersController (ShoppingCartDao shoppingCartDao,
                             OrderDao orderDao,
                             UserDao userDao)
    {
        this.shoppingCartDao = shoppingCartDao;
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    @PostMapping
    public void checkour (Principal principal)
    {

    }
}
