package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends InMemoryRepository<Product> {

    @Override
    protected String getId(Product product) {
        return product.getProductId();
    }

    @Override
    protected void setId(Product product, String id) {
        product.setProductId(id);
    }

    @Override
    protected boolean idMatches(Product product, String id) {
        return product.getProductId().equals(id);
    }

    @Override
    protected void updateEntity(Product existingProduct, Product updatedProduct) {
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setProductQuantity(updatedProduct.getProductQuantity());
    }
}
