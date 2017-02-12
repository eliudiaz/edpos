/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import ed.cracken.pos.ui.seller.to.ItemTo;
import org.vaadin.mockapp.samples.backend.data.Product;

/**
 *
 * @author edcracken
 */
public final class SellItemForm extends CssLayout {

    protected TextField productId;
    protected TextField description;
    protected TextField price;
    protected TextField quantity;
    protected TextField subtotal;
    protected TextField discount;

    protected Button save;
    protected Button cancel;
    protected Button delete;

    protected SellItemForm form;

    private BeanFieldGroup<ItemTo> fieldGroup;

    public SellItemForm() {

        setId("product-form");
        setStyleName("product-form-wrapper");
        addStyleName("product-form");
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setHeightUndefined();
        formLayout.setSpacing(true);
        formLayout.setStyleName("form-layout");
        formLayout.addComponent(productId = new TextField("Code"));
        formLayout.addComponent(description = new TextField("Description"));
        formLayout.addComponent(price = new TextField("Price"));
        formLayout.addComponent(quantity = new TextField("Quantity"));
        formLayout.addComponent(discount = new TextField("Discount"));
        formLayout.addComponent(subtotal = new TextField("Subtotal"));

        CssLayout separator = new CssLayout();
        separator.setStyleName("expander");
        formLayout.addComponent(separator);
        formLayout.addComponent(save = new Button("Aplicar") {
            {
                setStyleName("primary");
                setId("save");
            }
        });
        formLayout.addComponent(cancel = new Button("Cancelar") {
            {
                setStyleName("cancel");
                setId("cancel");
            }
        });
        formLayout.addComponent(delete = new Button("Quitar") {
            {
                setStyleName("danger");
                setId("delete");
            }
        });
        addComponent(formLayout);
        configBinding();
    }

    public void editItem(ItemTo product) {
        if (product == null) {
            product = new ItemTo();
        }
        fieldGroup.setItemDataSource(new BeanItem<ItemTo>(product));

        productId.setValidationVisible(false);
        description.setValidationVisible(false);
        price.setValidationVisible(false);
        discount.setValidationVisible(false);
        subtotal.setValidationVisible(false);

        productId.setReadOnly(true);
        description.setReadOnly(true);
        price.setReadOnly(true);
        discount.setReadOnly(true);
        subtotal.setReadOnly(true);

        String scrollScript = "window.document.getElementById('" + getId()
                + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);
    }

    private void formHasChanged() {
        description.setValidationVisible(true);
        delete.setEnabled(true);
    }

    private void configBinding() {
        fieldGroup = new BeanFieldGroup<>(ItemTo.class);
        fieldGroup.bindMemberFields(this);

        Property.ValueChangeListener valueListener = (Property.ValueChangeEvent event) -> {
            formHasChanged();
        };
        fieldGroup.getFields().stream().forEach((f) -> {
            f.addValueChangeListener(valueListener);
        });

        save.addClickListener((Button.ClickEvent event) -> {
            try {
                fieldGroup.commit();
                // update grid item
            } catch (FieldGroup.CommitException e) {
                Notification n = new Notification(
                        "Please re-check the fields", Notification.Type.ERROR_MESSAGE);
                n.setDelayMsec(500);
                n.show(getUI().getPage());
            }
        });

        cancel.addClickListener((Button.ClickEvent event) -> {
            // just cancel
        });

        delete.addClickListener((Button.ClickEvent event) -> {
            //remove from list
        });
    }

}
