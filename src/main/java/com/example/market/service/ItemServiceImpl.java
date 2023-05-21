package com.example.market.service;

import com.example.market.model.Items;
import com.example.market.repository.ItemRepository;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    @Override
    public List<Items> findAllItems() {
        List<Items> items = itemRepository.findAll();
        Collections.shuffle(items);
        return items;
    }
}
