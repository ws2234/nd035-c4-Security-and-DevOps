package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController,"orderRepository", orderRepository);
        TestUtils.injectObjects(orderController,"userRepository", userRepository);
    }

    @Test
    public void get_orders_for_user() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Basketball");
        item.setDescription("Ball used in a basketball game.");
        item.setPrice(new BigDecimal("35"));
        List<Item> items = new ArrayList<>();
        items.add(item);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(null);
        cart.setTotal(BigDecimal.valueOf(5));

        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("testPassword");
        user.setCart(null);

        cart.setUser(user);
        cart.setItems(items);

        user.setCart(cart);

        //orderController.submit("test");

        when(userRepository.findByUsername("test")).thenReturn(user);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void get_orders_for_user_null_user() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Basketball");
        item.setDescription("Ball used in a basketball game.");
        item.setPrice(new BigDecimal("35"));
        List<Item> items = new ArrayList<>();
        items.add(item);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(null);
        cart.setTotal(BigDecimal.valueOf(5));

        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("testPassword");
        user.setCart(null);

        cart.setUser(user);
        cart.setItems(items);

        user.setCart(cart);

        //orderController.submit("test");

        when(userRepository.findByUsername("test")).thenReturn(user);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("fail");

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
