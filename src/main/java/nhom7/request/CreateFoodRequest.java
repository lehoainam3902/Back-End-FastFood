package nhom7.request;

import java.util.List;

import nhom7.model.Category;
import nhom7.model.IngredientsItem;

public class CreateFoodRequest {
	
	private String name;
	private String description;
	private Long price;
	
	private Category category;
	private List<String> images;
	
	private Long restaurantId;
	private boolean vegetarin;
	private boolean seasonal;
	private List<IngredientsItem> ingredients;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public boolean isVegetarin() {
		return vegetarin;
	}
	public void setVegetarin(boolean vegetarin) {
		this.vegetarin = vegetarin;
	}
	public boolean isSeasonal() {
		return seasonal;
	}
	public void setSeasonal(boolean seasional) {
		this.seasonal = seasional;
	}
	public List<IngredientsItem> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<IngredientsItem> ingredients) {
		this.ingredients = ingredients;
	}
	public CreateFoodRequest(String name, String description, Long price, Category category, List<String> images,
			Long restaurantId, boolean vegetarin, boolean seasonal, List<IngredientsItem> ingredients) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.images = images;
		this.restaurantId = restaurantId;
		this.vegetarin = vegetarin;
		this.seasonal = seasonal;
		this.ingredients = ingredients;
	}
	public CreateFoodRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
