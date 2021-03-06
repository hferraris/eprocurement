package com.eprocurement.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping
	public String showDefaultItemsPage() {
		return "redirect:/item/all";
	}
	
	@GetMapping("/all")
	public String showAllItems(){
		return "items";
	}

	//get item form
	@GetMapping("/new")
	public String getItemForm(Model model) {
		model.addAttribute("item", new Item());
		return "item";
	}

	//save new item
	@PostMapping("/save")
	public String createNewItem(@ModelAttribute Item item) {
		itemRepository.save(item);
		return "redirect:/item/all";
	}

	//get item form for update
	@GetMapping("/{id}")
	public String getUpdateForm(@PathVariable Long id, Model model){
		if(itemRepository.findById(id).isPresent()){
			model.addAttribute("item", itemRepository.findById(id).get());
		}
		return "item";
	}

}
