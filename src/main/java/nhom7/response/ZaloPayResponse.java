package nhom7.response;

public class ZaloPayResponse {
    private String orderUrl;
    private int returnCode;
    private String returnMessage;

    // Getters and Setters
    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public ZaloPayResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ZaloPayResponse(String orderUrl) {
		super();
		this.orderUrl = orderUrl;
	}
	
}

