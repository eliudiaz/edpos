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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import ed.cracken.pos.ui.helpers.DataFormatHelper;
import ed.cracken.pos.ui.seller.to.ItemTo;
import java.math.BigDecimal;

/**
 *
 * @author edcracken
 */
public class SellerView extends CssLayout implements View {

    public static final String VIEW_NAME = "Ventas";

    private Button addProductBtn;
    private TextField productCode;
    private SellerLogic sellerLogic;
    private Label total;
    private Label totalValue;
    private Label quantity;
    private Label quantityValue;
    private Button saveTrx;
    private Button cancelTrx;
    private SellerGrid grid;

    public SellerView() {
        setSizeFull();
        addStyleName("crud-view");
        grid = new SellerGrid();
        grid.addSelectionListener(new SelectionEvent.SelectionListener() {

            @Override
            public void select(SelectionEvent event) {
//                grid.rowSelected(grid.getSelectedRow());
            }
        });

        VerticalLayout barAndGridLayout = new VerticalLayout();
        HorizontalLayout foot;
        barAndGridLayout.addComponent(createTopBar());
        barAndGridLayout.addComponent(grid);
        barAndGridLayout.addComponent(foot = createFooter());
        barAndGridLayout.setMargin(true);
        barAndGridLayout.setSpacing(true);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.setExpandRatio(grid, 1);
        barAndGridLayout.setStyleName("crud-main-layout");
        barAndGridLayout.setComponentAlignment(foot, Alignment.TOP_CENTER);
        addComponent(barAndGridLayout);

    }

    /**
     * top bar includes product's code
     * @return 
     */
    public HorizontalLayout createTopBar() {
        productCode = new TextField();
        productCode.setStyleName("filter-textfield");
        productCode.setInputPrompt("Codigo Producto");
        productCode.setImmediate(true);
        productCode.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
            sellerLogic.findAndAddProduct(event.getText());
        });

        addProductBtn = new Button("Buscar");
        addProductBtn.setIcon(FontAwesome.SEARCH);
        addProductBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addProductBtn.setIcon(FontAwesome.PLUS_CIRCLE);
        addProductBtn.addClickListener((Button.ClickEvent event) -> {
            sellerLogic.findAndAddProduct(productCode.getValue());
        });

        HorizontalLayout topLayout = new HorizontalLayout(productCode, addProductBtn);
        topLayout.setStyleName("top-bar");
        topLayout.setSpacing(true);
        return topLayout;
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

    }

    public void removeItem(ItemTo item) {

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
