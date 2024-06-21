package nhom7.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nhom7.model.Category;
import nhom7.model.Food;
import nhom7.model.Restaurant;
import nhom7.repository.FoodRepository;
import nhom7.request.CreateFoodRequest;

@Service
public class FoodServiceImp implements FoodService{

	
	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
		Food food = new Food();
		food.setFoodCategory(category);
		food.setRestaurant(restaurant);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngredients(req.getIngredients());
		food.setSeasonal(req.isSeasonal());
		
		food.setCreationDate(new Date());
		food.setVegetarian(req.isVegetarin());
		
		Food saveFood = foodRepository.save(food);
		restaurant.getFoods().add(saveFood);
		
		return saveFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		Food food = findFoodById(foodId);
		food.setRestaurant(null);
		foodRepository.save(food);
		
	}

	@Override
	public List<Food> getRestaurantFood(Long restaurantId, 
										boolean isVegitarain, 
										boolean isNonveg, 
										boolean isSeasonal, String foodCategory) {
		List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
		
		if (isVegitarain) {
			foods = filterByVegetarian(foods, isVegitarain);
		}
		if (isNonveg) {
			foods = filterByNonveg(foods, isNonveg);
		}
		if (isSeasonal) {
			foods = filterBySeasonal(foods, isSeasonal);
		}
		if (foodCategory!=null && !foodCategory.equals("")) {
			foods=filterByCatetory(foods, foodCategory);
			
		}
		return foods;
	}

	private List<Food> filterByCatetory(List<Food> foods, String foodCategory) {
		return foods.stream().filter(food -> {
			if(food.getFoodCategory()!=null){
				return food.getFoodCategory().getName().equals(foodCategory);
			}
			return false;
		}).collect(Collectors.toList());
	}

	private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
		return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
	}

	private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
		return foods.stream().filter(food -> food.isVegetarian()==false).collect(Collectors.toList());
	}

	private List<Food> filterByVegetarian(List<Food> foods, boolean isVegitarain) {
		return foods.stream().filter(food -> food.isVegetarian()==isVegitarain).collect(Collectors.toList());
	}

	@Override
	public List<Food> searchFood(String keyword) {
		return foodRepository.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		Optional<Food> optionalFood = foodRepository.findById(foodId);
		
		if(optionalFood.isEmpty()) {
			throw new Exception("Món ăn không tồn tại!!");
		}
		return optionalFood.get();
	}

	@Override
	public Food updateAvailabitityStatus(Long foodId) throws Exception {
		Food food = findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		return foodRepository.save(food);
	}

}
