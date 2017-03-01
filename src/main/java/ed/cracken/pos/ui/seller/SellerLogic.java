/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

import ed.cracken.pos.exception.ProductNotFoundException;
import ed.cracken.pos.ui.seller.to.ItemTo;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.vaadin.mockapp.samples.backend.DataService;
import ed.cracken.pos.backend.model.Product;
import ed.cracken.pos.ui.helpers.NotificationsHelper;
import ed.cracken.pos.ui.seller.to.SellPaymentTo;
import ed.cracken.pos.ui.seller.to.SellTransactionTo;

/**
 *
 * @author edcracken
 */
public class SellerLogic {

    private final SellerView view;
    private final List<ItemTo> items = new LinkedList<>();

    public SellerLogic(SellerView view) {
        this.view = view;
    }

    public void editItem(ItemTo item) {
        view.editItem(item);
    }

    /**
     * affects grid & footer
     *
     * @param product
     */
    public void addItem(Product product) {
        ItemTo item = ItemTo.builder()
                .description(product.getProductName())
                .price(product.getPrice())
                .quantity(BigDecimal.ONE)
                .product(product)
                .discount(BigDecimal.ZERO)
                .subtotal(product.getPrice().multiply(BigDecimal.ONE))
                .build();
        view.addItem(item);
        items.add(item);
    }

    public void updateItem(ItemTo item) {
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
    public void removeItem(ItemTo item) {
        view.removeItem(item);
    }

    public void findAndAddProduct(String code) {
        addItem(findProduct(code));
    }

    private Product findProduct(String code) {
        return DataService.get().getAllProducts()
                .stream()
                .filter(p -> p.getId().equals(Integer.valueOf(code)))
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }

    public void save(SellPaymentTo payment) {
        SellTransactionTo transaction = view.getTransaction();
        NotificationsHelper.showSaveNotification("Transaction finalizada!");
        view.newTransaction();
    }

}
