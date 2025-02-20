package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService service;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        String viewName = controller.createProductPage(model);
        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("CreateProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        String viewName = controller.createProductPost(product, model);
        verify(service).create(product);
        // Fix the expected value here:
        assertEquals("redirect:/product/list", viewName);
    }


    @Test
    void testProductListPage() {
        List<Product> products = new ArrayList<>();
        when(service.findAll()).thenReturn(products);

        String viewName = controller.productListPage(model);
        verify(model).addAttribute("products", products);
        assertEquals("ProductList", viewName);
    }

    @Test
    void testEditProductPageWhenProductIsNull() {
        when(service.findById("123")).thenReturn(null);
        String viewName = controller.editProductPage("123", model);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testEditProductPageWhenProductIsNotNull() {
        Product product = new Product();
        product.setProductId("123");
        when(service.findById("123")).thenReturn(product);

        String viewName = controller.editProductPage("123", model);
        verify(model).addAttribute("product", product);
        assertEquals("EditProduct", viewName);
    }

    @Test
    void testEditProductPost() {
        Product product = new Product();
        String viewName = controller.editProductPost("123", product);
        verify(service).update("123", product);
        assertEquals("redirect:/product/list", viewName);
        // Also verify that controller sets the productId on the model object
        assertEquals("123", product.getProductId());
    }

    @Test
    void testDeleteProduct() {
        String viewName = controller.deleteProduct("123");
        verify(service).delete("123");
        assertEquals("redirect:/product/list", viewName);
    }
}
