package nhom7.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import nhom7.model.Order;
import nhom7.response.PaymentResponse;
@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Value("${stripe.api.key}")
	private String stripeSecretKey;
	@Override
	public PaymentResponse createPaymentLink(Order order) throws StripeException {
		// TODO Auto-generated method stub
		
		Stripe.apiKey = stripeSecretKey;
		SessionCreateParams params = SessionCreateParams.builder().addPaymentMethodType(
				SessionCreateParams.
				PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl("http://localhost:3000/payment/success/"+order.getId())
				.setCancelUrl("http://localhost:3000/payment/fail")
				.addLineItem(SessionCreateParams.LineItem.builder()
						.setQuantity(1L).setPriceData(SessionCreateParams.LineItem.PriceData.builder()
						.setCurrency("vnd")	
						.setUnitAmount((long) order.getTotalPrice() + 30000)
						.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
								.setName("FOOD")
								.build() )
						.build()
							)
						.build()
				)
				.build();
		
		Session session = Session.create(params);
		
		PaymentResponse res = new PaymentResponse();
		res.setPayment_url(session.getUrl());
				
		return res;
	}
	
}
