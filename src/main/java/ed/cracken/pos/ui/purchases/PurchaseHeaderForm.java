/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.purchases;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Page;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import ed.cracken.pos.ui.components.DecimalNumberField;
import ed.cracken.pos.ui.seller.to.ItemTo;

/**
 *
 * @author edcracken
 */
public final class PurchaseHeaderForm extends CssLayout {

    protected TextField providerId;
    protected TextField providerName;
    protected TextField documentNumber;
    protected TextField documentDate;

    protected PurchaseHeaderForm form;

    private BeanFieldGroup<ItemTo> fieldGroup;

    public PurchaseHeaderForm(PurchaserLogic viewLogic) {

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setHeightUndefined();
        formLayout.setSpacing(true);
        formLayout.setStyleName("form-layout");
        formLayout.addComponent(providerId = new TextField("Code"));
        formLayout.addComponent(providerName = new TextField("Description"));
        formLayout.addComponent(documentNumber = new DecimalNumberField("Price"));
        formLayout.addComponent(documentDate = new DecimalNumberField("Quantity"));

        CssLayout separator = new CssLayout();
        separator.setStyleName("expander");
        formLayout.addComponent(separator);

        addComponent(formLayout);
        configBinding();
    }

    public void editItem(ItemTo product) {
        if (product == null) {
            product = new ItemTo();
        }
        fieldGroup.setItemDataSource(new BeanItem<>(product));
        providerName.setValidationVisible(false);
        providerName.setReadOnly(true);

    }

    private void formHasChanged() {
        providerName.setValidationVisible(true);
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

    }

}
