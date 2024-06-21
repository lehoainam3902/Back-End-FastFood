package nhom7.service;

import nhom7.model.Order;
import nhom7.model.OrderItem;
import nhom7.response.MomoResponse;
import nhom7.service.MomoService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MomoServiceImpl implements MomoService {

    private final String accessKey = "F8BBA842ECF85";
    private final String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
    private final String partnerCode = "MOMO";
    private final String ipnUrl = "https://webhook.site/b3088a6a-2d17-4f8d-a383-71389a6c600b";
    private final String requestType = "captureWallet";
    private final boolean autoCapture = true;
    private final String lang = "vi";

    @Override
    public MomoResponse createMomoPayment(Order order) throws Exception {
    	long totalPrice = order.getTotalPrice() + 30000;
        String orderId = partnerCode + new Date().getTime();
        String orderInfo = "Payment for the order #" + order.getId();
        String requestId = orderId;
        String extraData = "";
        String redirectUrl = "http://localhost:3000/payment/success/" + order.getId();
        String rawSignature = "accessKey=" + accessKey +
                              "&amount=" + totalPrice +
                              "&extraData=" + extraData +
                              "&ipnUrl=" + ipnUrl +
                              "&orderId=" + orderId +
                              "&orderInfo=" + orderInfo +
                              "&partnerCode=" + partnerCode +
                              "&redirectUrl=" + redirectUrl +
                              "&requestId=" + requestId +
                              "&requestType=" + requestType;

        String signature = hmacSHA256(secretKey, rawSignature);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("partnerCode", partnerCode);
        requestBody.put("partnerName", "Test");
        requestBody.put("storeId", "MomoTestStore");
        requestBody.put("requestId", requestId);
        requestBody.put("amount", totalPrice);
        requestBody.put("orderId", orderId);
        requestBody.put("orderInfo",orderInfo );
        requestBody.put("redirectUrl", redirectUrl);
        requestBody.put("ipnUrl", ipnUrl);
        requestBody.put("lang", lang);
        requestBody.put("requestType", requestType);
        requestBody.put("autoCapture", autoCapture);
        requestBody.put("extraData", extraData);
        requestBody.put("signature", signature);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(requestBody), headers);

        ResponseEntity<MomoResponse> response = restTemplate.postForEntity(
                "https://test-payment.momo.vn/v2/gateway/api/create",
                entity,
                MomoResponse.class
        );

        return response.getBody();
    }

    private static String hmacSHA256(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return bytesToHex(sha256_HMAC.doFinal(data.getBytes()));
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
