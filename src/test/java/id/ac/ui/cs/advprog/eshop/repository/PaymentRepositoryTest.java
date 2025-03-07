package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {

    private PaymentRepository repository;
    private List<Payment> samplePayments;

    @BeforeEach
    void initialize() {
        repository = new PaymentRepository();
        samplePayments = new ArrayList<>();

        Map<String, String> dataForPayment1 = new HashMap<>();
        dataForPayment1.put("voucherCode", "ESHOP10242018");
        Payment paymentA = new Payment(
                "fbfd7637-e4ef-4d0a-9db7-ed5c69c4081d",
                "voucherCode",
                dataForPayment1,
                PaymentStatus.SUCCESS.getValue()
        );
        samplePayments.add(paymentA);
        dataForPayment1.put("voucherCode", "ESHOP030407110323");
        Payment paymentB = new Payment(
                "e986e5e7-8155-4a1f-bbe1-1d0f3052b70f",
                "voucherCode",
                dataForPayment1,
                PaymentStatus.REJECTED.getValue()
        );
        samplePayments.add(paymentB);
    }

    @Test
    void testSaveAndRetrievePayment() {
        Payment toSave = samplePayments.get(1);
        Payment saved = repository.save(toSave);
        Payment fetched = repository.getPayment(toSave.getId());

        assertNotNull(saved);
        assertNotNull(fetched);
        assertEquals(toSave.getId(), saved.getId());
        assertEquals(toSave.getId(), fetched.getId());
        assertEquals(toSave.getMethod(), fetched.getMethod());
        assertEquals(toSave.getPaymentData(), fetched.getPaymentData());
        assertEquals(toSave.getStatus(), fetched.getStatus());
    }

    @Test
    void testUpdateExistingPayment() {
        Payment original = samplePayments.get(0);
        repository.save(original);

        Payment updatedVersion = new Payment(
                original.getId(),
                original.getMethod(),
                original.getPaymentData(),
                PaymentStatus.REJECTED.getValue()
        );
        Payment updateResult = repository.save(updatedVersion);
        Payment fetchedAfterUpdate = repository.getPayment(original.getId());

        assertNotNull(updateResult);
        assertEquals(original.getId(), updateResult.getId());
        assertEquals(PaymentStatus.REJECTED.getValue(), fetchedAfterUpdate.getStatus());
    }

    @Test
    void testRetrievePaymentThatExists() {
        for (Payment payment : samplePayments) {
            repository.save(payment);
        }

        Payment retrieved = repository.getPayment(samplePayments.get(1).getId());
        assertNotNull(retrieved);
        assertEquals(samplePayments.get(1).getId(), retrieved.getId());
        assertEquals(samplePayments.get(1).getMethod(), retrieved.getMethod());
        assertEquals(samplePayments.get(1).getPaymentData(), retrieved.getPaymentData());
        assertEquals(samplePayments.get(1).getStatus(), retrieved.getStatus());
    }

    @Test
    void testRetrieveNonExistingPayment() {
        for (Payment payment : samplePayments) {
            repository.save(payment);
        }

        Payment nonExistent = repository.getPayment("non-existent-id");
        assertNull(nonExistent);
    }

    @Test
    void testRetrieveAllStoredPayments() {
        for (Payment payment : samplePayments) {
            repository.save(payment);
        }

        List<Payment> allPayments = repository.getAllPayments();
        assertEquals(2, allPayments.size());

        Payment firstFetched = allPayments.get(0);
        Payment secondFetched = allPayments.get(1);

        assertEquals(samplePayments.get(0).getId(), firstFetched.getId());
        assertEquals(samplePayments.get(0).getMethod(), firstFetched.getMethod());
        assertEquals(samplePayments.get(0).getPaymentData(), firstFetched.getPaymentData());
        assertEquals(samplePayments.get(0).getStatus(), firstFetched.getStatus());

        assertEquals(samplePayments.get(1).getId(), secondFetched.getId());
        assertEquals(samplePayments.get(1).getMethod(), secondFetched.getMethod());
        assertEquals(samplePayments.get(1).getPaymentData(), secondFetched.getPaymentData());
        assertEquals(samplePayments.get(1).getStatus(), secondFetched.getStatus());
    }
}