package com.eprocurement.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemRestController {
	
	@Autowired 
	ItemRepository itemRepository;
	
	@GetMapping("/api/items")
	public Page<Item> getPagedItems(Pageable pageable, @RequestParam(defaultValue="") String searchBy, 
			@RequestParam(defaultValue="") String searchValue) {
		Page<Item> pageItem;
		
		switch (searchBy) {
		case "name" :
			pageItem = itemRepository.findByNameStartingWith(searchValue, pageable);
			break;
		case "description" :
			pageItem = itemRepository.findByDescriptionStartingWith(searchValue, pageable);
			break;	
		default:
			pageItem = itemRepository.findAll(pageable);	
			break;
		}	
		return pageItem;	
	}	
}
