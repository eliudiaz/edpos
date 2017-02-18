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
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import ed.cracken.pos.ui.components.DecimalNumberField;
import ed.cracken.pos.ui.purchases.to.PurchaseItemTo;
import ed.cracken.pos.ui.seller.to.ItemTo;
import ed.cracken.pos.ui.utils.UIHelper;
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
public final class PurchaseHeaderItemForm extends CssLayout {

    protected TextField id;
    protected TextField description;
    protected TextField price;
    protected TextField quantity;
    protected TextField subtotal;
    protected TextField discount;
    private Button addProductBtn;

    protected PurchaseHeaderItemForm form;

    private BeanFieldGroup<PurchaseItemTo> fieldGroup;

    public PurchaseHeaderItemForm(PurchaserLogic viewLogic) {

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setHeightUndefined();
        formLayout.setSpacing(true);
        formLayout.setStyleName("form-layout");
        formLayout.addComponent(UIHelper.buildComponentsRow(id = new TextField("Codigo"),
                description = new TextField("Nombre"),
                price = new DecimalNumberField("Precio"),
                quantity = new DecimalNumberField("Cantidad"),
                subtotal = new DecimalNumberField("Subtotal")));
        price.setConverter(BigDecimal.class);
        quantity.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
            if (!event.getText().isEmpty()) {
                try {
                    NumberFormat nf = DecimalFormat.getInstance();
                    nf.setMaximumFractionDigits(2);

                    PurchaseItemTo item;
                    BigDecimal r = BigDecimal
                            .valueOf(nf
                                    .parse(event.getText()).longValue())
                            .multiply((item = fieldGroup.getItemDataSource().getBean())
                                    .getPrice());
                    subtotal.setReadOnly(false);

                    subtotal.setValue(nf.format(r.doubleValue()));
                    subtotal.setReadOnly(true);
                } catch (ParseException ex) {
                    Logger.getLogger(PurchaseHeaderItemForm.class.getName()).log(Level.SEVERE, null, ex);
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

        id.setValidationVisible(false);
        description.setValidationVisible(false);
        price.setValidationVisible(false);
        discount.setValidationVisible(false);
        subtotal.setValidationVisible(false);

        id.setReadOnly(true);
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
    }

    private void configBinding() {
        fieldGroup = new BeanFieldGroup<>(PurchaseItemTo.class);
        fieldGroup.bindMemberFields(this);

        Property.ValueChangeListener valueListener = (Property.ValueChangeEvent event) -> {
            formHasChanged();
        };
        fieldGroup.getFields().stream().forEach((f) -> {
            f.addValueChangeListener(valueListener);
        });

    }

}
