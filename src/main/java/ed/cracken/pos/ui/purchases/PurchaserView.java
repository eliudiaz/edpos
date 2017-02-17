/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.purchases;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import ed.cracken.pos.ui.helpers.DataFormatHelper;
import ed.cracken.pos.ui.purchases.to.PurchaseItemTo;
import ed.cracken.pos.ui.seller.to.ItemTo;
import ed.cracken.pos.ui.seller.to.SellSummaryTo;
import java.math.BigDecimal;

/**
 *
 * @author edcracken
 */
public final class PurchaserView extends CssLayout implements View {

    public static final String VIEW_NAME = "Compras";

    //purchase information
    private TextField providerCode;
    private TextField providerName;
    private TextField documentNumber;
    private TextField documentDate;

    // product information
    private TextField productCode;
    private TextField productName;
    private TextField productPrice;
    private TextField productQuantity;
    private Label total;
    private Label totalValue;
    private Label quantity;
    private Label quantityValue;
    private Button addProductBtn;
    private Button saveTrx;
    private Button cancelTrx;
    private final PurchaserGrid grid;
    private final PurchaserLogic viewLogic;
    private final SellSummaryTo summary;
    private final PurchaserPaymentView paymentView;
    private final PurchaseItemSideForm purchaseItemForm;

    public PurchaserView() {

        summary = SellSummaryTo.builder().count(BigDecimal.ZERO).total(BigDecimal.ZERO).build();
        setSizeFull();
        addStyleName("crud-view");
        viewLogic = new PurchaserLogic(this);
        paymentView = new PurchaserPaymentView();
        grid = new PurchaserGrid();
        grid.addSelectionListener((SelectionEvent event) -> {
            viewLogic.editItem(grid.getSelectedRow());
        });

        VerticalLayout mainContent = new VerticalLayout();
        HorizontalLayout foot;
        mainContent.addComponent(createTopBar());
        mainContent.addComponent(grid);
        mainContent.addComponent(foot = createFooter());
        mainContent.setMargin(true);
        mainContent.setSpacing(true);
        mainContent.setSizeFull();
        mainContent.setExpandRatio(grid, 1);
        mainContent.setStyleName("crud-main-layout");
        mainContent.setComponentAlignment(foot, Alignment.TOP_CENTER);
        addComponent(mainContent);
        addComponent(purchaseItemForm = new PurchaseItemSideForm(viewLogic));
    }

    public void editItem(PurchaseItemTo item) {
        if (item != null) {
            purchaseItemForm.addStyleName("visible");
            purchaseItemForm.setEnabled(true);
        } else {
            purchaseItemForm.removeStyleName("visible");
            purchaseItemForm.setEnabled(false);
        }
        purchaseItemForm.editItem(item);
    }

    public void updateItem(PurchaseItemTo item) {
        grid.refresh(item);
        purchaseItemForm.removeStyleName("visible");
        purchaseItemForm.setEnabled(false);
    }

    public void cancelItemEdit() {
        purchaseItemForm.removeStyleName("visible");
        purchaseItemForm.setEnabled(false);
    }

    public void removeItem(PurchaseItemTo item) {
        grid.remove(item);
        purchaseItemForm.removeStyleName("visible");
        purchaseItemForm.setEnabled(false);
    }

    /**
     * top bar includes product's code
     *
     * @return
     */
    public HorizontalLayout createTopBar() {

        productCode = new TextField();
        productCode.setStyleName("filter-textfield");
        productCode.setInputPrompt("Codigo Producto");
        productCode.setImmediate(true);
        productCode.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
            if (!event.getText().isEmpty()) {
                viewLogic.findAndAddProduct(event.getText());
            }
        });

        productName = new TextField();
        productPrice = new TextField();
        productQuantity = new TextField();

        addProductBtn = new Button("Buscar");
        addProductBtn.setIcon(FontAwesome.SEARCH);
        addProductBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addProductBtn.setIcon(FontAwesome.PLUS_CIRCLE);
        addProductBtn.addClickListener((Button.ClickEvent event) -> {
            if (!productCode.getValue().isEmpty()) {
                viewLogic.findAndAddProduct(productCode.getValue());
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout(productCode, addProductBtn);
        topLayout.setStyleName("top-bar");
        topLayout.setSpacing(true);
        return topLayout;
    }

    private void refreshInternal() {

        totalValue.setValue("<h2><strong>" + DataFormatHelper.formatNumber(summary.getTotal()) + "</strong></h2>");
        quantityValue.setValue("<h2><strong>" + DataFormatHelper.formatNumber(summary.getCount()) + "</strong></h2>");
    }

    public HorizontalLayout createFooter() {
        total = new Label("<h2><strong>Total:</strong></h2>");
        total.setContentMode(ContentMode.HTML);
        totalValue = new Label("<h2><strong>" + DataFormatHelper.formatNumber(BigDecimal.ZERO) + "</strong></h2>");
        totalValue.setContentMode(ContentMode.HTML);

        quantity = new Label("<h2><strong>Cantidad:</strong></h2>");
        quantity.setContentMode(ContentMode.HTML);
        quantityValue = new Label("<h2><strong>" + DataFormatHelper.formatNumber(BigDecimal.ZERO) + "</strong></h2>");
        quantityValue.setContentMode(ContentMode.HTML);

        saveTrx = new Button("Guardar", FontAwesome.CHECK_CIRCLE_O);
        saveTrx.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveTrx.setHeight("60px");
        saveTrx.addClickListener((Button.ClickEvent event) -> {
            UI.getCurrent().addWindow(paymentView);
        });
        cancelTrx = new Button("Cancelar", FontAwesome.CLOSE);
        cancelTrx.addStyleName(ValoTheme.BUTTON_DANGER);
        cancelTrx.setHeight("60px");
        HorizontalLayout bottom = new HorizontalLayout();
        HorizontalLayout labelsArea = new HorizontalLayout();
        HorizontalLayout buttonsArea = new HorizontalLayout();

        labelsArea.setSpacing(true);
        labelsArea.addComponent(total);
        labelsArea.addComponent(totalValue);
        labelsArea.addComponent(quantity);
        labelsArea.addComponent(quantityValue);

        labelsArea.setComponentAlignment(total, Alignment.MIDDLE_LEFT);
        labelsArea.setComponentAlignment(totalValue, Alignment.MIDDLE_LEFT);
        labelsArea.setComponentAlignment(quantity, Alignment.MIDDLE_RIGHT);
        labelsArea.setComponentAlignment(quantityValue, Alignment.MIDDLE_RIGHT);

        buttonsArea.setSpacing(true);
        buttonsArea.addComponent(saveTrx);
        buttonsArea.addComponent(cancelTrx);
        bottom.setSpacing(true);
        bottom.addComponent(buttonsArea);
        bottom.addComponent(labelsArea);
        bottom.setComponentAlignment(buttonsArea, Alignment.MIDDLE_LEFT);
        bottom.setComponentAlignment(labelsArea, Alignment.MIDDLE_RIGHT);
        bottom.setWidth("100%");

        return bottom;
    }

    public void addItem(ItemTo item) {
        grid.add(item);
        summary.setCount(summary.getCount().add(item.getQuantity()));
        summary.setTotal(summary.getTotal().add(item.getSubtotal()));
        refreshInternal();
    }

    private void refreshFooter() {

    }

    /**
     *
     */
    public void createFootBar() {
        saveTrx = new Button("Grabar");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        System.out.println(">> entered");
    }

}
