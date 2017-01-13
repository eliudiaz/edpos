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
 * @author eliud
 */
public class SellerView extends CssLayout implements View {

    public static final String VIEW_NAME = "Ventas";

    private Button addProductBtn;
    private TextField productCode;
    private SellerLogic sellerLogic;
    private Label total;
    private Label totalValue;
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
        barAndGridLayout.addComponent(createTopBar());
        barAndGridLayout.addComponent(grid);
        barAndGridLayout.addComponent(createFooter());
        barAndGridLayout.setMargin(true);
        barAndGridLayout.setSpacing(true);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.setExpandRatio(grid, 1);
        barAndGridLayout.setStyleName("crud-main-layout");
        addComponent(barAndGridLayout);
    }

    /**
     * top bar includes product's code
     */
    public HorizontalLayout createTopBar() {
        productCode = new TextField();
        productCode.setStyleName("filter-textfield");
        productCode.setInputPrompt("Codigo Producto");
        productCode.setImmediate(true);
        productCode.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                sellerLogic.findAndAddProduct(event.getText());
            }
        });

        addProductBtn = new Button("Buscar");
        addProductBtn.setIcon(FontAwesome.SEARCH);
        addProductBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addProductBtn.setIcon(FontAwesome.PLUS_CIRCLE);
        addProductBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                sellerLogic.findAndAddProduct(productCode.getValue());
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout(productCode, addProductBtn);
        topLayout.setStyleName("top-bar");
        topLayout.setSpacing(true);
//        topLayout.setWidth("100%");

        return topLayout;
    }

    public HorizontalLayout createFooter() {
        total = new Label("Total:");
        
        totalValue = new Label(DataFormatHelper.formatNumber(BigDecimal.ZERO));
        saveTrx = new Button("Guardar",FontAwesome.CHECK_CIRCLE_O);
        cancelTrx = new Button("Cancelar",FontAwesome.C);
        HorizontalLayout bottomLayout = new HorizontalLayout();
        bottomLayout.setSpacing(true);
        bottomLayout.addComponent(total);
        bottomLayout.addComponent(totalValue);
        bottomLayout.setComponentAlignment(total, Alignment.MIDDLE_LEFT);
        bottomLayout.setComponentAlignment(totalValue, Alignment.MIDDLE_LEFT);
        bottomLayout.addComponent(saveTrx);
        bottomLayout.addComponent(cancelTrx);
        bottomLayout.setComponentAlignment(saveTrx, Alignment.MIDDLE_RIGHT);
        bottomLayout.setComponentAlignment(cancelTrx, Alignment.MIDDLE_RIGHT);
        return bottomLayout;
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
