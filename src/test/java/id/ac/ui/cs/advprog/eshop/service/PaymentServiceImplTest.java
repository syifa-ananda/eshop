package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockStatic;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    List<Payment> payments;
    List<Order> orders;
    Map<String, String> paymentData1;
    Map<String, String> paymentData2;

    @BeforeAll
    static void mocking() {
        UUID mockUUID = UUID.fromString("aaaa1111-bbbb-2222-cccc-3333dddd4444");
        mockStatic(UUID.class);
        when(UUID.randomUUID()).thenReturn(mockUUID);
    }

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();

        paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP9876ABC5432");
        Payment successPayment1 = new Payment(
                "aaaa1111-bbbb-2222-cccc-3333dddd4444",
                "voucherCode",
                paymentData1,
                PaymentStatus.SUCCESS.getValue()
        );
        payments.add(successPayment1);

        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "ESHOPXYZ12345");
        Payment rejectedPayment1 = new Payment(
                "aaaa1111-bbbb-2222-cccc-3333dddd4444",
                "voucherCode",
                invalidVoucherData,
                PaymentStatus.REJECTED.getValue()
        );
        payments.add(rejectedPayment1);

        paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "BCA");
        paymentData2.put("referenceCode", "44444444-5555-6666-7777-888888888888");

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("33333333-4444-5555-6666-777777777777");
        product1.setProductName("Laptop Ultra G");
        product1.setProductQuantity(1);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order(
                "22222222-3333-4444-5555-666666666666",
                products,
                1710000000L,
                "Jane Smith"
        );
        orders.add(order1);

        Order order2 = new Order(
                "22222222-3333-4444-5555-666666666666",
                products,
                1710000000L,
                "Jane Smith"
        );
        orders.add(order2);
    }

    @Test
    void AddPaymentSuccessfully() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());

        Payment result = paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(order.getId(), paymentService.getPaymentMapping().get(payment.getId()));
    }

    @Test
    void AddPaymentRejected() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        Payment result = paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());

        assertEquals(order.getId(), paymentService.getPaymentMapping().get(payment.getId()));
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void CreatePaymentByVoucherCodeNullVoucher() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());

        paymentData1.put("voucherCode", null);
        Payment payment = paymentService.addPayment(order, "voucherCode", paymentData1);

        assertEquals("aaaa1111-bbbb-2222-cccc-3333dddd4444", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertNull(payment.getPaymentData().get("voucherCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void CreatePaymentByVoucherNotInData() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.addPayment(order, "voucherCode", paymentData2));
    }

    @Test
    void CreatePaymentByBankTransferCorrect() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());

        Payment payment = paymentService.addPayment(order, "bankTransfer", paymentData2);

        assertEquals("aaaa1111-bbbb-2222-cccc-3333dddd4444", payment.getId());
        assertEquals("bankTransfer", payment.getMethod());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("44444444-5555-6666-7777-888888888888", payment.getPaymentData().get("referenceCode"));
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void CreatePaymentByBankTransferEmptyBankName() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());

        paymentData2.put("bankName", "");
        Payment payment = paymentService.addPayment(order, "bankTransfer", paymentData2);

        assertEquals("aaaa1111-bbbb-2222-cccc-3333dddd4444", payment.getId());
        assertEquals("bankTransfer", payment.getMethod());
        assertEquals("", payment.getPaymentData().get("bankName"));
        assertEquals("44444444-5555-6666-7777-888888888888", payment.getPaymentData().get("referenceCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void CreatePaymentByBankTransferNoBank() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("referenceCode", "55555555-6666-7777-8888-999999999999");

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.addPayment(order, "bankTransfer", paymentData));
    }

    @Test
    void CreatePaymentByBankTransferNoReferenceCode() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Mandiri");

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.addPayment(order, "bankTransfer", paymentData));
    }

    @Test
    void SetStatusToSuccess() {
        Payment payment = payments.get(1);
        Order order = orders.get(1);
        order.setStatus(OrderStatus.SUCCESS.getValue());

        doReturn(payment).when(paymentRepository).getPayment(payment.getId());
        doReturn(order).when(orderRepository).findById(order.getId());

        paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());

        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).findById(order.getId());

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).getPayment(payment.getId());
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderRepository, times(2)).save(order);
    }

    @Test
    void SetStatusToReject() {
        Payment payment = payments.get(0); // initially SUCCESS
        Order order = orders.get(0);
        order.setStatus(OrderStatus.FAILED.getValue());

        doReturn(payment).when(paymentRepository).getPayment(payment.getId());
        doReturn(order).when(orderRepository).findById(order.getId());

        paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());

        payment.setStatus(PaymentStatus.REJECTED.getValue());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).getPayment(payment.getId());
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderRepository, times(2)).save(order);
    }

    @Test
    void GetPaymentIfPaymentExists() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).getPayment(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void GetAllPayment() {
        doReturn(payments).when(paymentRepository).getAllPayments();

        List<Payment> results = paymentService.getAllPayments();
        for (int i = 0; i < payments.size(); i++) {
            assertEquals(payments.get(i).getId(), results.get(i).getId());
        }
        assertEquals(2, results.size());
    }
}
