package nhom7.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import nhom7.model.Order;
import nhom7.response.PaypalResponse;

@Service
public class PaypalServiceImpl implements PaypalServices {
    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;
    
    @Value("${exchange.rate.vnd.usd}")
    private double exchangeRate;

    @Override
    public PaypalResponse createPaypalLink(Order order) throws PayPalRESTException {
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);

        // Convert VND to USD
        double totalPriceVND = order.getTotalPrice().doubleValue();
        double totalPriceUSD = totalPriceVND / exchangeRate;

        // Create amount details
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", totalPriceUSD));

        // Transaction information
        Transaction transaction = new Transaction();
        transaction.setDescription("Order payment");
        transaction.setAmount(amount);

        // Add transaction to a list
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // Set payment details
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        // Redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:3000/payment/fail");
        redirectUrls.setReturnUrl("http://localhost:3000/payment/success/" + order.getId());

        // Create payment object
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        // Create payment
        Payment createdPayment = payment.create(apiContext);

        // Extract approval URL
        String approvalUrl = createdPayment.getLinks().stream()
            .filter(link -> link.getRel().equalsIgnoreCase("approval_url"))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Approval URL not found"))
            .getHref();

        // Prepare response
        PaypalResponse response = new PaypalResponse();
        response.setPaypal_url(approvalUrl);

        return response;
    }
}