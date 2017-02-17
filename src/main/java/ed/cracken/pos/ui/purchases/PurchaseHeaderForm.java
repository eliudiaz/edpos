/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.purchases;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.Page;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import ed.cracken.pos.ui.components.DecimalNumberField;
import ed.cracken.pos.ui.seller.to.ItemTo;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edcracken
 */
public final class PurchaseHeaderForm extends CssLayout {

    protected TextField providerId;
    protected TextField providerName;
    protected TextField documentNumber;
    protected TextField documentDate;
    protected TextField subtotal;

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
        formLayout.addComponent(subtotal = new DecimalNumberField("Subtotal"));
        documentNumber.setConverter(BigDecimal.class);
        documentDate.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
            if (!event.getText().isEmpty()) {
                try {
                    NumberFormat nf = DecimalFormat.getInstance();
                    nf.setMaximumFractionDigits(2);

                    ItemTo item;
                    BigDecimal r = BigDecimal
                            .valueOf(nf
                                    .parse(event.getText()).longValue())
                            .multiply((item = fieldGroup.getItemDataSource().getBean())
                                    .getPrice());
                    subtotal.setReadOnly(false);

                    subtotal.setValue(nf.format(r.doubleValue()));
                    subtotal.setReadOnly(true);
                } catch (ParseException ex) {
                    Logger.getLogger(PurchaseHeaderForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

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

        providerId.setValidationVisible(false);
        providerName.setValidationVisible(false);
        documentNumber.setValidationVisible(false);
        subtotal.setValidationVisible(false);

        providerId.setReadOnly(true);
        providerName.setReadOnly(true);
        documentNumber.setReadOnly(true);
        subtotal.setReadOnly(true);

        String scrollScript = "window.document.getElementById('" + getId()
                + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);
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
