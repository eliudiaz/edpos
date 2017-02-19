/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.purchases;

import ed.cracken.pos.exception.ProductNotFoundException;
import ed.cracken.pos.ui.purchases.to.PurchaseItemTo;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.backend.data.Product;

/**
 *
 * @author edcracken
 */
public class PurchaserLogic {

    private final PurchaserView view;
    private final List<PurchaseItemTo> items = new LinkedList<>();

    public PurchaserLogic(PurchaserView view) {
        this.view = view;
    }

    public void editItem(PurchaseItemTo item) {
        view.editItem(item);
    }

    /**
     * affects grid & footer
     *
     * @param product
     */
    public void addItem(Product product) {
        PurchaseItemTo item = PurchaseItemTo.builder()
                .name(product.getProductName())
                .price(product.getPrice())
                .quantity(BigDecimal.ONE)
                .product(product)
                .build();
        view.addItem(item);
        items.add(item);
    }

    public void showItem(Product product) {
        PurchaseItemTo item = PurchaseItemTo.builder()
                .name(product.getProductName())
                .price(product.getPrice())
                .quantity(BigDecimal.ONE)
                .product(product)
                .build();
        view.showItem(item);
    }

    public void updateItem(PurchaseItemTo item) {
        view.updateItem(item);
    }

    public void cancelItemChanges() {
        view.cancelItemEdit();
    }

    /**
     * affects grid & footer
     *
     * @param item
     */
    public void removeItem(PurchaseItemTo item) {
        view.removeItem(item);
    }

    public void findAndAddProduct(String code) {
        addItem(findProduct(code));
    }

    public void findAndShowProduct(String code) {
        showItem(findProduct(code));
    }

    private Product findProduct(String code) {
        return DataService.get().getAllProducts()
                .stream()
                .filter(p -> p.getId().equals(Integer.valueOf(code)))
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }

}
