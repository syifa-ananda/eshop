package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Product product = new Product();
        when(productRepository.create(product)).thenReturn(product);

        Product created = service.create(product);

        verify(productRepository).create(product);
        assertEquals(product, created);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());

        Iterator<Product> mockIterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(mockIterator);

        List<Product> result = service.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        Product product = new Product();
        when(productRepository.findById("123")).thenReturn(product);

        Product found = service.findById("123");
        verify(productRepository).findById("123");
        assertEquals(product, found);
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        when(productRepository.update("123", product)).thenReturn(product);

        Product updated = service.update("123", product);
        verify(productRepository).update("123", product);
        assertEquals(product, updated);
    }

    @Test
    void testDelete() {
        service.delete("123");
        verify(productRepository).delete("123");
    }
}
