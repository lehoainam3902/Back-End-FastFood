package nhom7.service;

import java.io.UnsupportedEncodingException;

import nhom7.model.Order;
import nhom7.response.VNPayResponse;

public interface VNPayService {
	public VNPayResponse createVNPayLink(Order order) throws UnsupportedEncodingException;
}
