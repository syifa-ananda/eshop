package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    Order order;

    @Test
    void testCreatePaymentSuccessfully() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "DISCOUNT1111XYZ");
        Payment payment = new Payment("1111aaaa-2222-bbbb-3333-cccc4444dddd", "voucherCode", paymentData,
                PaymentStatus.SUCCESS.getValue());
        assertEquals("1111aaaa-2222-bbbb-3333-cccc4444dddd", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("DISCOUNT1111XYZ", payment.getPaymentData().get("voucherCode"));
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentForInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "DISCOUNT1111XYZ");
        assertThrows(IllegalArgumentException.class,
                () ->new Payment("1111aaaa-2222-bbbb-3333-cccc4444dddd", "voucherCode", paymentData,
                        "MEOW"));
    }

    @Test
    void testSetStatusForSuccessStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "DISCOUNT2222ABC");
        Payment payment = new Payment("1111aaaa-2222-bbbb-3333-cccc4444dddd", "voucherCode", paymentData,
                PaymentStatus.REJECTED.getValue());
        assertEquals("REJECTED", payment.getStatus());
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusForInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "DISCOUNT2222ABC");
        Payment payment = new Payment("1111aaaa-2222-bbbb-3333-cccc4444dddd", "voucherCode", paymentData,
                PaymentStatus.REJECTED.getValue());
        assertThrows(IllegalArgumentException.class,
                () -> payment.setStatus("MEOW"));
    }
}