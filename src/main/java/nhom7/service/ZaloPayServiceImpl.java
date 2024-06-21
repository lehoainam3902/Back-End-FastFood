package nhom7.service;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import crypto.HMACUtil;
import nhom7.model.Order;
import nhom7.model.OrderItem;
import nhom7.response.ZaloPayResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ZaloPayServiceImpl implements ZaloPayService {

    @Value("${zalopay.app_id}")
    private String appId;

    @Value("${zalopay.key1}")
    private String key1;

    @Value("${zalopay.endpoint}")
    private String endpoint;

    @Override
    public ZaloPayResponse createZaloPayLink(Order order) throws Exception {
        Random rand = new Random();
        int randomId = rand.nextInt(1000000);
        
        
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("app_id", appId);
        String appTransId = getCurrentTimeString("yyMMdd") + "_" + order.getId();
        orderData.put("app_trans_id", appTransId);
        long appTime = System.currentTimeMillis();
        orderData.put("app_time", appTime);
        String appUser = "user123";
        orderData.put("app_user", appUser);
        
        
        orderData.put("description", "Payment for the order #" + order.getId());
        orderData.put("bank_code", "");
       
        // Extracting items from the order
        JSONArray itemsJsonArray = new JSONArray();
        long totalPrice = 0;
        
        for (OrderItem item : order.getItems()) {
            JSONObject itemJson = new JSONObject();
       /*     itemJson.put("itemid", item.getId());
            itemJson.put("itemname", item.getFood().getName());
            itemJson.put("itemprice", item.getTotalPrice()); // Price in VND
            itemJson.put("itemquantity", item.getQuantity());*/
            totalPrice += item.getTotalPrice() + 30000;
            itemsJsonArray.put(itemJson);
        }
        orderData.put("amount", totalPrice);

        Map<String, String> embedData = new HashMap<>();
        embedData.put("redirecturl", "http://localhost:3000/payment/success/" + order.getId());
        
        String itemsJson = itemsJsonArray.toString();
        String embedDataJson = new JSONObject(embedData).toString();
        
        orderData.put("item", itemsJson);
        orderData.put("embed_data", embedDataJson);

        // Construct the data for HMAC
        String data = orderData.get("app_id") +"|"+ orderData.get("app_trans_id") +"|"+ orderData.get("app_user") +"|"+ orderData.get("amount")
        +"|"+ orderData.get("app_time") +"|"+ orderData.get("embed_data") +"|"+ orderData.get("item");
                
               
        
        
        /*String orderData2 = orderData.get("app_id") +"|"+ orderData.get("app_trans_id") +"|"+ orderData.get("app_user") +"|"+ orderData.get("amount")
        +"|"+ orderData.get("app_time") +"|"+ orderData.get("embeddata") +"|"+ orderData.get("item");*/
        

        // Generate the HMAC
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data);
        orderData.put("mac", mac);
       
        

        // Debugging: Print orderData and data for HMAC
        System.out.println("Order Data: " + orderData);
       
        System.out.println("mac: " + mac);

        // Sending the HTTP POST request
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(endpoint);

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> entry : orderData.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }

        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        JSONObject result = new JSONObject(resultJsonStr.toString());
        System.out.println("ZaloPay API Response: " + result.toString());  // Log the response

        ZaloPayResponse response = new ZaloPayResponse();
        response.setOrderUrl(result.getString("order_url"));
      

        return response;
    }

    private String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }
}
