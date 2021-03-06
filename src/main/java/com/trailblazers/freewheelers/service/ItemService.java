package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.model.Item;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface ItemService {
	
	Item get(Long item_id);

    Item getByName(String name);

	void delete(Item item);
	
	List<Item> findAll();

    List<Item> getItemsWithNonZeroQuantity();
	
	void saveAll(List<Item> items);

	void refreshItemList(Item item);

    void deleteItems(List<Item> items);

    void decreaseQuantityByOne(Item item);

    Item saveItem(Item item);

    HashMap<Item, Long> getItemHashMap(HttpServletRequest request);
}
