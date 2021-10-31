package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void get_all_items() {
        ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void get_item_by_id() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Basketball");
        item.setDescription("Ball used in a basketball game.");
        item.setPrice(new BigDecimal("35"));

        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Item i = response.getBody();
        assertNotNull(i);
        assertEquals(Long.valueOf(1L), i.getId());
        assertEquals("Basketball", i.getName());
        assertEquals("Ball used in a basketball game.", i.getDescription());
        assertEquals(BigDecimal.valueOf(35), i.getPrice());
    }

    @Test
    public void get_item_by_name() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Basketball");
        item.setDescription("Ball used in a basketball game.");
        item.setPrice(new BigDecimal("35"));

        List<Item> items = new ArrayList<>();
        items.add(item);

        when(itemRepository.findByName(item.getName())).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Basketball");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> i = response.getBody();
        assertNotNull(i);
        assertEquals(Long.valueOf(1L), i.get(0).getId());
        assertEquals("Basketball", i.get(0).getName());
        assertEquals("Ball used in a basketball game.", i.get(0).getDescription());
        assertEquals(BigDecimal.valueOf(35), i.get(0).getPrice());
    }
}
