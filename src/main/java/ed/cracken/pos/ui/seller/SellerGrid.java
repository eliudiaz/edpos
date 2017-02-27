/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.NumberRenderer;
import ed.cracken.pos.ui.helpers.DataFormatHelper;
import ed.cracken.pos.ui.seller.to.ItemTo;
import java.text.DecimalFormat;
import java.util.Collection;

/**
 *
 * @author edcracken
 */
public final class SellerGrid extends Grid {

    public SellerGrid() {
        DecimalFormat df = (DecimalFormat) DataFormatHelper.getFormatter();
        setSizeFull();
        setSelectionMode(SelectionMode.SINGLE);
        setContainerDataSource(new BeanItemContainer<ItemTo>(
                ItemTo.class));

        setColumnOrder("productId", "description", "price", "quantity", "discount", "subtotal");
        getColumn("productId").setHeaderCaption("Codigo");
        getColumn("description").setHeaderCaption("Descripcion");

        getColumn("price")
                .setHeaderCaption("Precio U.")
                .setRenderer(new NumberRenderer(df));
        getColumn("quantity").setHeaderCaption("Cantidad");
        getColumn("subtotal")
                .setHeaderCaption("Subtotal")
                .setRenderer(new NumberRenderer(df));
        getColumn("discount")
                .setHeaderCaption("Descuento")
                .setRenderer(new NumberRenderer(df));
        getColumn("product").setHidden(true);

    }

    public void refresh(ItemTo pItem) {
        BeanItem<ItemTo> item = getContainer().getItem(pItem);
        if (item != null) {
            MethodProperty p = (MethodProperty) item.getItemProperty("productId");
            p.fireValueChange();
        } else {
            getContainer().addBean(pItem);
        }
    }

    private BeanItemContainer<ItemTo> getContainer() {
        return (BeanItemContainer<ItemTo>) super.getContainerDataSource();
    }

    public void add(ItemTo item) {
        item.setProductId(item.getProduct().getId().toString());
        getContainer().addBean(item);
    }

    public void remove(ItemTo product) {
        getContainer().removeItem(product);
    }

    public void setItems(Collection<ItemTo> items) {
        getContainer().removeAllItems();
        getContainer().addAll(items);
    }

    public Collection<ItemTo> getItems() {
        return getContainer().getItemIds();
    }

    @Override
    public ItemTo getSelectedRow() throws IllegalStateException {
        return (ItemTo) super.getSelectedRow();
    }

}
