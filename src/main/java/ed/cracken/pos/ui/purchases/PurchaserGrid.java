/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.purchases;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.NumberRenderer;
import ed.cracken.pos.ui.helpers.DataFormatHelper;
import ed.cracken.pos.ui.purchases.to.PurchaseItemTo;
import java.text.DecimalFormat;
import java.util.Collection;

/**
 *
 * @author edcracken
 */
public final class PurchaserGrid extends Grid {

    public PurchaserGrid() {
        DecimalFormat df = (DecimalFormat) DataFormatHelper.getFormatter();
        setSizeFull();
        setSelectionMode(SelectionMode.SINGLE);
        setContainerDataSource(new BeanItemContainer<>(
                PurchaseItemTo.class));

        setColumnOrder("code", "name", "price", "quantity", "subtotal");
        getColumn("code").setHeaderCaption("Codigo");
        getColumn("name").setHeaderCaption("Descripcion");

        getColumn("price")
                .setHeaderCaption("Precio U.")
                .setRenderer(new NumberRenderer(df));
        getColumn("quantity").setHeaderCaption("Cantidad");
        getColumn("subtotal")
                .setHeaderCaption("Subtotal")
                .setRenderer(new NumberRenderer(df));

        getColumn("product").setHidden(true);

    }

    public void refresh(PurchaseItemTo pItem) {
        BeanItem<PurchaseItemTo> item = getContainer().getItem(pItem);
        if (item != null) {
            MethodProperty p = (MethodProperty) item.getItemProperty("productId");
            p.fireValueChange();
        } else {
            getContainer().addBean(pItem);
        }
    }

    private BeanItemContainer<PurchaseItemTo> getContainer() {
        return (BeanItemContainer<PurchaseItemTo>) super.getContainerDataSource();
    }

    public void add(PurchaseItemTo item) {
        item.setCode(item.getProduct().getId().toString());
        getContainer().addBean(item);
    }

    public void remove(PurchaseItemTo product) {
        getContainer().removeItem(product);
    }

    public void setItems(Collection<PurchaseItemTo> items) {
        getContainer().removeAllItems();
        getContainer().addAll(items);
    }

    @Override
    public PurchaseItemTo getSelectedRow() throws IllegalStateException {
        return (PurchaseItemTo) super.getSelectedRow();
    }

}
