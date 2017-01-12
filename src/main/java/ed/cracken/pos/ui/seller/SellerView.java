/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import ed.cracken.pos.ui.seller.to.ItemTo;

/**
 *
 * @author eliud
 */
public class SellerView extends CssLayout implements View {

    private Button addProductBtn;
    private TextField productCode;
    private SellerLogic sellerLogic;
    private Label total;
    private Label totalValue;
    private Button finishTrx;
    
    public SellerView() {
        setSizeFull();
        addStyleName("crud-view");
    }

    /**
     * top bar includes product's code
     */
    public void createTopBar() {
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
        addProductBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addProductBtn.setIcon(FontAwesome.PLUS_CIRCLE);
        addProductBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                sellerLogic.findAndAddProduct(productCode.getValue());
            }
        });
    }

    public void addItem(ItemTo item) {
        
    }
    
    public void removeItem(ItemTo item){
        
    }
    
    private void refreshFooter(){
        
    }
    

    /**
     *
     */
    public void createFootBar() {
        finishTrx=new Button("Grabar");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
