package nhom7.service;

import com.stripe.exception.StripeException;

import nhom7.model.Order;
import nhom7.response.PaymentResponse;

public interface PaymentService {
	public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
