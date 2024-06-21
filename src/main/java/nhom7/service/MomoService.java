package nhom7.service;

import nhom7.model.Order;
import nhom7.response.MomoResponse;

public interface MomoService {
    MomoResponse createMomoPayment(Order order) throws Exception;
}

