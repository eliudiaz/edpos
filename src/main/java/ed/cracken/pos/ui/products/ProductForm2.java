/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.products;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.Collection;
import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.backend.data.Availability;
import org.vaadin.mockapp.samples.backend.data.Category;
import org.vaadin.mockapp.samples.backend.data.Product;

/**
 *
 * @author edcracken
 */
public class ProductForm2 extends CssLayout {

    protected TextField productName;
    protected TextField price;
    protected TextField stockCount;
    protected ComboBox availability;
    protected CategoryField category;
    protected Button save;
    protected Button cancel;
    protected Button delete;
    private SampleCrudLogic viewLogic;
    private BeanFieldGroup<Product> fieldGroup;

    public void initComponents() {
        setId("product-form");
        setStyleName("product-form-wrapper");
        addStyleName("product-form");
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setHeightUndefined();
        formLayout.setSpacing(true);
        formLayout.setStyleName("form-layout");
        formLayout.addComponent(productName = new TextField("Name"));
        HorizontalLayout priceAndStockLayout = new HorizontalLayout();
        priceAndStockLayout.addComponent(price = new TextField("Price"));
        priceAndStockLayout.addComponent(stockCount = new TextField("Stock"));
        formLayout.addComponent(priceAndStockLayout);
        formLayout.addComponent(availability = new ComboBox("Availability"));
        formLayout.addComponent(category = new CategoryField("Category"));
        CssLayout separator = new CssLayout();
        separator.setStyleName("expander");
        formLayout.addComponent(separator);
        formLayout.addComponent(save = new Button("Save") {
            {
                setStyleName("primary");
                setId("save");
            }
        });
        formLayout.addComponent(cancel = new Button("Cancel") {
            {
                setStyleName("cancel");
                setId("cancel");
            }
        });
        formLayout.addComponent(delete = new Button("Delete") {
            {
                setStyleName("danger");
                setId("delete");
            }
        });
        addComponent(formLayout);

    }

    public ProductForm2(SampleCrudLogic sampleCrudLogic) {
        super();
        viewLogic = sampleCrudLogic;
        initComponents();
        price.setConverter(new EuroConverter());

        for (Availability s : Availability.values()) {
            availability.addItem(s);
        }

        fieldGroup = new BeanFieldGroup<Product>(Product.class);
        fieldGroup.bindMemberFields(this);

        // perform validation and enable/disable buttons while editing
        Property.ValueChangeListener valueListener = new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                formHasChanged();
            }
        };
        for (Field f : fieldGroup.getFields()) {
            f.addValueChangeListener(valueListener);
        }

        fieldGroup.addCommitHandler(new FieldGroup.CommitHandler() {

            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent)
                    throws FieldGroup.CommitException {
            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent)
                    throws FieldGroup.CommitException {
                DataService.get().updateProduct(
                        fieldGroup.getItemDataSource().getBean());
            }
        });

        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    fieldGroup.commit();

                    // only if validation succeeds
                    Product product = fieldGroup.getItemDataSource().getBean();
                    viewLogic.saveProduct(product);
                } catch (FieldGroup.CommitException e) {
                    Notification n = new Notification(
                            "Please re-check the fields", Notification.Type.ERROR_MESSAGE);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                }
            }
        });

        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                viewLogic.cancelProduct();
            }
        });

        delete.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Product product = fieldGroup.getItemDataSource().getBean();
                viewLogic.deleteProduct(product);
            }
        });
    }

    public void setCategories(Collection<Category> categories) {
        category.setOptions(categories);
    }

    public void editProduct(Product product) {
        if (product == null) {
            product = new Product();
        }
        fieldGroup.setItemDataSource(new BeanItem<Product>(product));

        // before the user makes any changes, disable validation error indicator
        // of the product name field (which may be empty)
        productName.setValidationVisible(false);

        // Scroll to the top
        // As this is not a Panel, using JavaScript
        String scrollScript = "window.document.getElementById('" + getId()
                + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);
    }

    private void formHasChanged() {
        // show validation errors after the user has changed something
        productName.setValidationVisible(true);

        // only products that have been saved should be removable
        boolean canRemoveProduct = false;
        BeanItem<Product> item = fieldGroup.getItemDataSource();
        if (item != null) {
            Product product = item.getBean();
            canRemoveProduct = product.getId() != -1;
        }
        delete.setEnabled(canRemoveProduct);
    }
}
