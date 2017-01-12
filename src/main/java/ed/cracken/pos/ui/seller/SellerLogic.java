/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

import ed.cracken.pos.ui.seller.to.ItemTo;
import org.vaadin.mockapp.samples.backend.data.Product;

/**
 *
 * @author eliud
 */
public class SellerLogic {

    private SellerView view;

    public SellerLogic(SellerView view) {
        this.view = view;
    }

    /**
     * affects grid & footer
     *
     * @param product
     */
    public void addItem(Product product) {
        view.addItem(null);
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
    }

    private Product findProduct(String code) {
        return null;
    }

}
