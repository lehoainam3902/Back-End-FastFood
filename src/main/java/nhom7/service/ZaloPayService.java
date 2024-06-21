package nhom7.service;

import nhom7.model.Order;
import nhom7.response.ZaloPayResponse;


public interface ZaloPayService {
    ZaloPayResponse createZaloPayLink(Order order) throws Exception;
}