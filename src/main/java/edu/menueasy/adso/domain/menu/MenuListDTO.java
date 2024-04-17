package edu.menueasy.adso.domain.menu;

import edu.menueasy.adso.domain.category.Category;

public record MenuListDTO(
		Integer id,
		String title, 
		String description,
		Double price,
		Boolean state,
		String imagename,
		Category category

	){

	public MenuListDTO(Menu menu) {
		this(
				menu.getId(),
				menu.getTitle(),
				menu.getDescription(),
				menu.getPrice(),
				menu.getState(),
				menu.getImagename(),
				menu.getCategory()
		);

	}

}
