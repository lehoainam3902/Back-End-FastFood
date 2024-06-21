	package nhom7.service;

import java.util.List;

import dto.RestaurantDto;
import nhom7.model.Restaurant;
import nhom7.model.User;
import nhom7.request.CreateRestaurantRequest;

public interface RestaurantService {
	
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user);
	
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception;

	public void deleteRestaurant(Long restaurantId) throws Exception;
	
	public List<Restaurant> getAllRestaurant();
	
	public List<Restaurant> searchRestaurant(String keyword);
	
	public Restaurant findRestaurantById(Long id) throws Exception;
	
	public Restaurant getRestaurantByUserId(Long userId) throws Exception;
	
	public RestaurantDto addToFavories(Long restaurantId, User user) throws Exception;
	
	public Restaurant updateRestaurantStatus (Long id) throws Exception;
}
