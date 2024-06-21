package nhom7.service;

import java.util.List;

import nhom7.model.Category;
import nhom7.model.Food;
import nhom7.model.Restaurant;
import nhom7.request.CreateFoodRequest;

public interface FoodService {
	
	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);
	
	void deleteFood(Long foodId) throws Exception;
	
	public List<Food> getRestaurantFood(Long restaurantId, 
										boolean isVegitarain, 
										boolean isNonveg, 
										boolean isSeasonal, 
										String foodCategory
	);
	
	public List<Food> searchFood(String keyword);
	
	public Food findFoodById(Long foodId) throws Exception;
	
	public Food updateAvailabitityStatus (Long foodId) throws Exception;
}
