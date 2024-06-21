package nhom7.request;

import nhom7.model.Address;
import nhom7.model.ContactInformation;

import java.util.List;

public class CreateRestaurantRequest {

	private Long id;
	private String name;
	private String description;
	private String cuisineType;
	private Address address;
	private ContactInformation contactInformation;
	private String opningHours;
	private List<String> images;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getCuisineType() {
		return cuisineType;
	}
	public void setCuisineType(String cuisineType) {
		this.cuisineType = cuisineType;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public ContactInformation getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(ContactInformation contactInformation) {
		this.contactInformation = contactInformation;
	}
	public String getOpningHours() {
		return opningHours;
	}
	public void setOpningHours(String opningHours) {
		this.opningHours = opningHours;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public CreateRestaurantRequest(Long id, String name, String description, String cuisineType, Address address,
			ContactInformation contactInformation, String opningHours, List<String> images) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.cuisineType = cuisineType;
		this.address = address;
		this.contactInformation = contactInformation;
		this.opningHours = opningHours;
		this.images = images;
	}
	public CreateRestaurantRequest() {
		super();
		// TODO Auto-generated constructor stub
	}	
}
