/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author edcracken
 */
public final class SellerPaymentView extends Window {

    public SellerPaymentView() {
        super("Subs on Sale"); // Set window caption
        center();

        VerticalLayout content = new VerticalLayout();
        content.addComponent(new Label("Ingrese forma de pago:"));
        setContent(content);

        setClosable(false);
        setModal(true);

        Button ok = new Button("OK");
        ok.addClickListener((ClickEvent event) -> {
            close(); 
        });
        content.addComponent(ok);
    }

}
