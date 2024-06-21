package nhom7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import nhom7.model.CartItem;
import nhom7.model.Order;
import nhom7.model.User;
import nhom7.request.AddCartItemRequest;
import nhom7.request.OrderRequest;
import nhom7.response.MomoResponse;
import nhom7.response.PaymentResponse;
import nhom7.response.PaypalResponse;
import nhom7.response.VNPayResponse;
import nhom7.response.ZaloPayResponse;
import nhom7.service.MomoService;
import nhom7.service.OrderService;
import nhom7.service.PaymentService;
import nhom7.service.PaypalServices;
import nhom7.service.UserService;
import nhom7.service.VNPayService;
import nhom7.service.ZaloPayService;

@RestController
@CrossOrigin
@RequestMapping("/api")

public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VNPayService vnPayService;
	
	@Autowired
	private PaypalServices paypalServices;
	
	@Autowired
	private ZaloPayService zaloPayService;
	
	@Autowired
	private MomoService momoService;
	//VNPay
//	@PostMapping("/order")
//	public ResponseEntity<VNPayResponse> createOrder(@RequestBody OrderRequest req,
//			@RequestHeader("Authorization") String jwt) throws Exception {
//		User user = userService.findUserByJwtToken(jwt);
//		Order order = orderService.createOrder(req, user);
//		VNPayResponse res = vnPayService.createVNPayLink(order);
//		//PaymentResponse res=paymentService.createPaymentLink(order);
//		return new ResponseEntity<>(res, HttpStatus.OK);
//	}
	// Stripe
//	@PostMapping("/order")
//	public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest req,
//			@RequestHeader("Authorization") String jwt) throws Exception {
//		User user = userService.findUserByJwtToken(jwt);
//		Order order = orderService.createOrder(req, user);
//		//VNPayResponse res = vnPayService.createVNPayLink(order);
//		PaymentResponse res=paymentService.createPaymentLink(order);
//		return new ResponseEntity<>(res, HttpStatus.OK);
//	}
	
	@PostMapping("/order")
	public ResponseEntity<?> createOrder(@RequestBody OrderRequest req,
	                                     @RequestHeader("Authorization") String jwt) throws Exception {
	    User user = userService.findUserByJwtToken(jwt);
	    Order order = orderService.createOrder(req, user);

	    // Determine the payment method and create the appropriate payment link
	    if ("vnpay".equalsIgnoreCase(req.getPaymentMethod())) {
	        VNPayResponse res = vnPayService.createVNPayLink(order);
	        return new ResponseEntity<>(res, HttpStatus.OK);
	    } else if ("payment".equalsIgnoreCase(req.getPaymentMethod())) {
	        PaymentResponse res = paymentService.createPaymentLink(order);
	        return new ResponseEntity<>(res, HttpStatus.OK); 
	    }else if("paypal".equalsIgnoreCase(req.getPaymentMethod())) {
	    	PaypalResponse res = paypalServices.createPaypalLink(order);
	    	return new ResponseEntity<>(res, HttpStatus.OK); 
	    }else if("zalopay".equalsIgnoreCase(req.getPaymentMethod())) {
	    	ZaloPayResponse res = zaloPayService.createZaloPayLink(order);
	    	return new ResponseEntity<>(res, HttpStatus.OK); 
	    }else if("momo".equalsIgnoreCase(req.getPaymentMethod())) {
	    	MomoResponse res = momoService.createMomoPayment(order);
	    	return new ResponseEntity<>(res, HttpStatus.OK); 
	    }
	    	else {
	        // Handle unknown payment method
	        return new ResponseEntity<>("Invalid payment method", HttpStatus.BAD_REQUEST);
	    }
	}
	@GetMapping("/order/user")
	public ResponseEntity<List<Order>> getOrderHistory(
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Order> orders = orderService.getUsersOrder(user.getId());
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
}
