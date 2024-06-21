package nhom7.model;


public class ContactInformation {
	private String email;
	private String mobile;
	private String twitter;
	private String instagram;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getInstagram() {
		return instagram;
	}
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
	public ContactInformation(String email, String mobile, String twitter, String instagram) {
		super();
		this.email = email;
		this.mobile = mobile;
		this.twitter = twitter;
		this.instagram = instagram;
	}
	public ContactInformation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
