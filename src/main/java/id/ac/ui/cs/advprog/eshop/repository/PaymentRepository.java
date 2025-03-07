package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PaymentRepository {

    private Map<String, Payment> paymentStore = new LinkedHashMap<>();
    public Payment save(Payment payment) {
        paymentStore.put(payment.getId(), payment);
        return payment;
    }

    public Payment getPayment(String id) {
        return paymentStore.get(id);
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(paymentStore.values());
    }
}