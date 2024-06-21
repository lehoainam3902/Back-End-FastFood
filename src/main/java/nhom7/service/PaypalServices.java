package nhom7.service;

import java.io.UnsupportedEncodingException;

import com.paypal.base.rest.PayPalRESTException;

import nhom7.model.Order;
import nhom7.response.PaypalResponse;

public interface PaypalServices {
	public PaypalResponse createPaypalLink(Order order) throws PayPalRESTException;


}
