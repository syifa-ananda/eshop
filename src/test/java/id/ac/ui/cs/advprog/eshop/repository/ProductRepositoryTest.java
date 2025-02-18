package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductExistingProduct() {
        Product product = new Product();
        product.setProductId("product-001");
        product.setProductName("Delete Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        productRepository.delete("product-001");

        assertNull(productRepository.findById("product-001"));

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteProductNonExistingProduct() {
        Product product = new Product();
        product.setProductId("product-002");
        product.setProductName("Sample Product");
        product.setProductQuantity(20);
        productRepository.create(product);

        productRepository.delete("non-existent-id");

        Product retrievedProduct = productRepository.findById("product-002");
        assertNotNull(retrievedProduct);
        assertEquals("Sample Product", retrievedProduct.getProductName());
        assertEquals(20, retrievedProduct.getProductQuantity());
    }

    @Test
    void testUpdateExistingProduct() {
        Product product = new Product();
        product.setProductId("product-003");
        product.setProductName("Old Product");
        product.setProductQuantity(15);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("product-003");
        updatedProduct.setProductName("Modified Product");
        updatedProduct.setProductQuantity(30);
        Product result = productRepository.update(updatedProduct.getProductId(), updatedProduct);

        assertNotNull(result);
        assertEquals("Modified Product", result.getProductName());
        assertEquals(30, result.getProductQuantity());

        Product retrievedProduct = productRepository.findById("product-003");
        assertNotNull(retrievedProduct);
        assertEquals("Modified Product", retrievedProduct.getProductName());
        assertEquals(30, retrievedProduct.getProductQuantity());
    }

    @Test
    void testUpdateNonExistingProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Ghost Product");
        updatedProduct.setProductQuantity(40);

        Product result = productRepository.update(updatedProduct.getProductId(), updatedProduct);
        assertNull(result);
    }

    @Test
    void testCreateProductWithNullId() {
        Product product = new Product();
        product.setProductName("Null ID Product");
        product.setProductQuantity(10);

        Product createdProduct = productRepository.create(product);

        assertNotNull(createdProduct.getProductId());
        assertFalse(createdProduct.getProductId().isEmpty());
        assertEquals("Null ID Product", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());
    }

    @Test
    void testCreateProductWithEmptyId() {
        Product product = new Product();
        product.setProductId("");
        product.setProductName("Empty ID Product");
        product.setProductQuantity(5);

        Product createdProduct = productRepository.create(product);

        assertNotNull(createdProduct.getProductId());
        assertFalse(createdProduct.getProductId().isEmpty());
        assertEquals("Empty ID Product", createdProduct.getProductName());
        assertEquals(5, createdProduct.getProductQuantity());
    }

    @Test
    void testUpdateExistingProductInSecondIteration() {
        // Create first product
        Product product1 = new Product();
        product1.setProductId("p1");
        product1.setProductName("First Product");
        product1.setProductQuantity(5);
        productRepository.create(product1);

        // Create second product
        Product product2 = new Product();
        product2.setProductId("p2");
        product2.setProductName("Second Product");
        product2.setProductQuantity(10);
        productRepository.create(product2);

        // Prepare the updated product for the second one
        Product updatedProduct2 = new Product();
        updatedProduct2.setProductId("p2");
        updatedProduct2.setProductName("Second Product Updated");
        updatedProduct2.setProductQuantity(20);

        // Update the second product
        Product result = productRepository.update("p2", updatedProduct2);

        // Verify it updated correctly
        assertNotNull(result);
        assertEquals("p2", result.getProductId());
        assertEquals("Second Product Updated", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }
}
