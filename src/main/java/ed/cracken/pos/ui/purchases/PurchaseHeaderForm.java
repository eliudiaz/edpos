/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.purchases;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import ed.cracken.pos.ui.purchases.to.PurchaseTo;
import static ed.cracken.pos.ui.utils.UIHelper.buildComponentsRow;

/**
 *
 * @author edcracken
 */
public final class PurchaseHeaderForm extends CssLayout {

    protected TextField providerId;
    protected TextField providerName;
    protected TextField documentNumber;
    protected DateField documentDate;

    protected PurchaseHeaderForm form;

    private BeanFieldGroup<PurchaseTo> fieldGroup;

    public PurchaseHeaderForm(PurchaserLogic viewLogic) {

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setHeightUndefined();
        formLayout.setSpacing(true);
        formLayout.setStyleName("form-layout");
        formLayout.addComponent(buildComponentsRow(providerId = new TextField("Codigo Proveedor:"),
                providerName = new TextField("Nombre Proveedor")));
        formLayout.addComponent(buildComponentsRow(documentNumber = new TextField("No. Factura"),
                documentDate = new DateField("Fecha Factura")));

        CssLayout separator = new CssLayout();
        separator.setStyleName("expander");
        formLayout.addComponent(separator);

        addComponent(formLayout);
        configBinding();
    }

    public PurchaseTo getPurchaseHeader() {
        return fieldGroup.getItemDataSource().getBean();
    }

    private void formHasChanged() {
        providerName.setValidationVisible(true);
    }

    private void configBinding() {
        fieldGroup = new BeanFieldGroup<>(PurchaseTo.class);
        fieldGroup.bindMemberFields(this);

        Property.ValueChangeListener valueListener = (Property.ValueChangeEvent event) -> {
            formHasChanged();
        };
        fieldGroup.getFields().stream().forEach((f) -> {
            f.addValueChangeListener(valueListener);
        });

    }

}
