/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.purchases;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import ed.cracken.pos.ui.components.DecimalNumberField;
import ed.cracken.pos.ui.helpers.DataFormatHelper;
import ed.cracken.pos.ui.purchases.to.PurchaseItemTo;
import ed.cracken.pos.ui.helpers.UIHelper;
import java.math.BigDecimal;

public final class PurchaseHeaderItemForm extends CssLayout {

    protected TextField id;
    protected TextField name;
    protected TextField price;
    protected TextField quantity;
    protected TextField subtotal;
    private Button addProductBtn;

    protected PurchaseHeaderItemForm form;

    private BeanFieldGroup<PurchaseItemTo> fieldGroup;

    public PurchaseHeaderItemForm(PurchaserLogic viewLogic) {
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setHeightUndefined();
        formLayout.setSpacing(true);
        formLayout.setStyleName("form-layout");
        HorizontalLayout fields;
        Panel purchaseItemArea = new Panel("Nuevo Producto",
                fields = UIHelper.buildComponentsRow(id = new TextField("Codigo"),
                        name = new TextField("Nombre"),
                        price = new DecimalNumberField("Precio"),
                        quantity = new DecimalNumberField("Cantidad"),
                        subtotal = new DecimalNumberField("Subtotal"),
                        addProductBtn = new Button("Agregar")));
        addProductBtn.setIcon(FontAwesome.SEARCH);
        addProductBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addProductBtn.setIcon(FontAwesome.PLUS_CIRCLE);
        addProductBtn.addClickListener((Button.ClickEvent event) -> {
            try {
                fieldGroup.commit();
                viewLogic.addItem(fieldGroup.getItemDataSource().getBean());
                setItem(null);
            }
            catch (FieldGroup.CommitException ex) {
                ex.printStackTrace(System.err);
            }
        }
        );

        id.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
            if (!event.getText().isEmpty()) {
                viewLogic.findAndShowProduct(event.getText());
            }
        });
        id.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        price.setConverter(BigDecimal.class);
        quantity.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
            if (!event.getText().isEmpty()) {
                subtotal.setReadOnly(false);
                subtotal.setValue(
                        DataFormatHelper.formatNumber(
                                fieldGroup.getItemDataSource()
                                        .getBean()
                                        .getPrice()
                                        .multiply(BigDecimal
                                                .valueOf(Float
                                                        .valueOf(event.getText())))));
                subtotal.setReadOnly(true);
            }
        });
        id.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        formLayout.addComponent(purchaseItemArea);
        fields.setComponentAlignment(addProductBtn, Alignment.BOTTOM_LEFT);
        fields.setMargin(true);

        CssLayout separator = new CssLayout();
        separator.setStyleName("expander");
        formLayout.addComponent(separator);

        addComponent(formLayout);
        configBinding();
        setItem(null);
    }

    public void setItem(PurchaseItemTo product) {
        if (product == null) {
            product = PurchaseItemTo
                    .builder()
                    .code("")
                    .name("")
                    .quantity(BigDecimal.ZERO)
                    .price(BigDecimal.ZERO)
                    .build();
        }
        product.setQuantity(BigDecimal.ONE);
        product.setSubtotal(product.getPrice().multiply(product.getQuantity()));
        fieldGroup.setItemDataSource(new BeanItem<>(product));

        id.setValidationVisible(false);
        name.setValidationVisible(false);
        price.setValidationVisible(false);
        subtotal.setValidationVisible(false);

        name.setReadOnly(true);
        price.setReadOnly(true);
        subtotal.setReadOnly(true);
    }

    private void formHasChanged() {
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
