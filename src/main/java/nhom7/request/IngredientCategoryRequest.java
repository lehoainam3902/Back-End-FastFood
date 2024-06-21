package nhom7.request;

public class IngredientCategoryRequest {
	private String name;
	private Long restaurantId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public IngredientCategoryRequest(String name, Long restaurantId) {
		super();
		this.name = name;
		this.restaurantId = restaurantId;
	}
	public IngredientCategoryRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
