package com.asset.management;

import com.asset.management.model.Category;
import com.asset.management.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AssetManagementApplication  {

	public static void main(String[] args) {
		SpringApplication.run(AssetManagementApplication.class, args);

	}

	@Bean
	CommandLineRunner initDatabase(CategoryRepository categoryRepository){
		return args-> {
			List<String> predefinedCategories = List.of("Hardware", "Software", "Accessories");

			for (String categoryName : predefinedCategories) {
				if (categoryRepository.findByCategoryName(categoryName).isEmpty()) {
					categoryRepository.save(new Category(categoryName));
				}
			}
		};
	}



}
