package org.vaadin.mockapp.samples.backend.mock;

import java.util.List;

import org.vaadin.mockapp.samples.backend.DataService;
import ed.cracken.pos.backend.model.Category;
import ed.cracken.pos.backend.model.Product;
import ed.cracken.pos.backend.model.Provider;
import java.util.Collection;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockDataService extends DataService {

    private static MockDataService INSTANCE;

    private final List<Product> products;
    private final List<Category> categories;
    private final List<Provider> providers;
    private int nextProductId = 0;

    private MockDataService() {
        categories = MockDataGenerator.createCategories();
        products = MockDataGenerator.createProducts(categories);
        nextProductId = products.size() + 1;
        providers = MockDataGenerator.createProviders();
    }

    public synchronized static DataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MockDataService();
        }
        return INSTANCE;
    }

    @Override
    public synchronized List<Product> getAllProducts() {
        return products;
    }

    @Override
    public synchronized List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public synchronized void updateProduct(Product p) {
        if (p.getId() < 0) {
            // New product
            p.setId(nextProductId++);
            products.add(p);
            return;
        }
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == p.getId()) {
                products.set(i, p);
                return;
            }
        }

        throw new IllegalArgumentException("No product with id " + p.getId()
                + " found");
    }

    @Override
    public synchronized Product getProductById(int productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == productId) {
                return products.get(i);
            }
        }
        return null;
    }

    @Override
    public synchronized void deleteProduct(int productId) {
        Product p = getProductById(productId);
        if (p == null) {
            throw new IllegalArgumentException("Product with id " + productId
                    + " not found");
        }
        products.remove(p);
    }

    @Override
    public synchronized Collection<Provider> getAllProviders() {
        return providers;
    }
}
