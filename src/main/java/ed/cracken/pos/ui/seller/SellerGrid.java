/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;
import ed.cracken.pos.ui.seller.to.ItemTo;

/**
 *
 * @author eliud
 */
public class SellerGrid extends Grid {

    public SellerGrid() {
        BeanItemContainer<ItemTo> container = new BeanItemContainer<ItemTo>(
                ItemTo.class);
        setContainerDataSource(container);
        setColumnOrder("productId", "description", "price", "quantity", "subtotal");
        getColumn("productId").setHeaderCaption("Codigo");
        getColumn("description").setHeaderCaption("Descripcion");
        getColumn("price").setHeaderCaption("Precio U.");
        getColumn("quantity").setHeaderCaption("Cantidad");
        getColumn("subtotal").setHeaderCaption("Subtotal");
    }

}
