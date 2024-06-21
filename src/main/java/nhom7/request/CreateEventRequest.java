package nhom7.request;

import java.time.LocalDateTime;

public class CreateEventRequest {

    private String name;
    private String description;
    private LocalDateTime date;
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
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public CreateEventRequest(String name, String description, LocalDateTime date) {
		super();
		this.name = name;
		this.description = description;
		this.date = date;
	}
	public CreateEventRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

    
}
