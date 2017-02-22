package org.vaadin.mockapp.samples.backend;

import java.util.Collection;

import ed.cracken.pos.backend.model.Category;
import ed.cracken.pos.backend.model.Product;
import org.vaadin.mockapp.samples.backend.mock.MockDataService;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class DataService {

    public abstract Collection<Product> getAllProducts();

    public abstract Collection<Category> getAllCategories();

    public abstract void updateProduct(Product p);

    public abstract void deleteProduct(int productId);

    public abstract Product getProductById(int productId);

    public static DataService get() {
        return MockDataService.getInstance();
    }

}
