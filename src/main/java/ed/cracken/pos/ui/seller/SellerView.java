/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

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
import ed.cracken.pos.ui.seller.to.ItemTo;
import ed.cracken.pos.ui.seller.to.SellPaymentTo;
import ed.cracken.pos.ui.seller.to.SellSummaryTo;
import ed.cracken.pos.ui.seller.to.SellTransactionTo;
import java.math.BigDecimal;

/**
 *
 * @author edcracken
 */
public final class SellerView extends CssLayout implements View {

    public static final String VIEW_NAME = "Ventas";

    private Button addProductBtn;
    private TextField txtProductCode;

    private Label total;
    private Label totalValue;
    private Label quantity;
    private Label quantityValue;
    private Button saveTrx;
    private Button cancelTrx;
    private SellerGrid grid;
    private SellSummaryTo summary;
    private final SellerLogic viewLogic;
    private final SellItemForm sellItemForm;

    private void initGrid() {
        grid = new SellerGrid();
        grid.addSelectionListener((SelectionEvent event) -> {
            viewLogic.editItem(grid.getSelectedRow());
        });
    }

    public SellerView() {
        setSizeFull();
        addStyleName("crud-view");
        viewLogic = new SellerLogic(this);

        VerticalLayout transactionDetailArea = new VerticalLayout();
        HorizontalLayout foot;
        transactionDetailArea.addComponent(createTopBar());
        initGrid();
        initTransaction();
        transactionDetailArea.addComponent(grid);
        transactionDetailArea.addComponent(foot = createFooter());
        transactionDetailArea.setMargin(true);
        transactionDetailArea.setSpacing(true);
        transactionDetailArea.setSizeFull();
        transactionDetailArea.setExpandRatio(grid, 1);
        transactionDetailArea.setStyleName("crud-main-layout");
        transactionDetailArea.setComponentAlignment(foot, Alignment.TOP_CENTER);
        addComponent(transactionDetailArea);
        addComponent(sellItemForm = new SellItemForm(viewLogic));

    }

    private void initTransaction() {
        txtProductCode.setValue("");
        summary = SellSummaryTo.builder()
                .count(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .build();
    }

    public void newTransaction() {
        initGrid();
        initTransaction();
    }

    public void editItem(ItemTo item) {
        if (item != null) {
            sellItemForm.addStyleName("visible");
            sellItemForm.setEnabled(true);
        } else {
            sellItemForm.removeStyleName("visible");
            sellItemForm.setEnabled(false);
        }
        sellItemForm.editItem(item);
    }

    public void updateItem(ItemTo item) {
        grid.refresh(item);
        sellItemForm.removeStyleName("visible");
        sellItemForm.setEnabled(false);
    }

    public void cancelItemEdit() {
        sellItemForm.removeStyleName("visible");
        sellItemForm.setEnabled(false);
    }

    public void removeItem(ItemTo item) {
        grid.remove(item);
        sellItemForm.removeStyleName("visible");
        sellItemForm.setEnabled(false);
    }

    /**
     * top bar includes product's code
     *
     * @return
     */
    public HorizontalLayout createTopBar() {
        txtProductCode = new TextField();
        txtProductCode.setStyleName("filter-textfield");
        txtProductCode.setInputPrompt("Codigo Producto");
        txtProductCode.setImmediate(true);
        txtProductCode.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
            if (!event.getText().isEmpty()) {
                viewLogic.findAndAddProduct(event.getText());
            }
        });

        addProductBtn = new Button("Buscar");
        addProductBtn.setIcon(FontAwesome.SEARCH);
        addProductBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addProductBtn.setIcon(FontAwesome.PLUS_CIRCLE);
        addProductBtn.addClickListener((Button.ClickEvent event) -> {
            if (!txtProductCode.getValue().isEmpty()) {
                viewLogic.findAndAddProduct(txtProductCode.getValue());
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout(txtProductCode, addProductBtn);
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
            UI.getCurrent().addWindow(
                    new SellerPaymentView(new SellPaymentTo(summary.getTotal()), viewLogic));
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

    public SellTransactionTo getTransaction() {
        return new SellTransactionTo(summary, grid.getItems());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
