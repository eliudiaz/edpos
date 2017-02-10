/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author edcracken
 */
public final class SellerPaymentView extends Window {

    private final TextField txtCash;
    private final TextField txtCard;

    public SellerPaymentView() {
        super("Forma de pago"); // Set window caption
        center();
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        content.setSpacing(true);

        HorizontalLayout lyCash = new HorizontalLayout();
        HorizontalLayout lyCard = new HorizontalLayout();
        content.addComponent(lyCash);
        content.addComponent(lyCard);

        lyCash.addComponent(new Label("Efectivo: "));
        lyCash.setSpacing(true);
        lyCash.addComponent(txtCash = new TextField());

        lyCard.addComponent(new Label("Tarjeta: "));
        lyCard.addComponent(txtCard = new TextField());
        lyCard.setSpacing(true);

        setContent(content);
        setClosable(false);
        setModal(true);

        Button ok = new Button("Aceptar");
        ok.addClickListener((ClickEvent event) -> {
            close();
        });
        content.addComponent(ok);

        Button ko = new Button("Cancelar");
        ok.addClickListener((ClickEvent event) -> {
            close();
        });
        content.addComponent(ko);
    }

}
