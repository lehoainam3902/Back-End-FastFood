package nhom7.request;

public class IngredientRequest {
	private String name;
	private Long categoryId;
	private Long restaurantId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public IngredientRequest(String name, Long categoryId, Long restaurantId) {
		super();
		this.name = name;
		this.categoryId = categoryId;
		this.restaurantId = restaurantId;
	}
	public IngredientRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
