/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import ed.cracken.pos.ui.components.DecimalNumberField;
import ed.cracken.pos.ui.helpers.DataFormatHelper;
import ed.cracken.pos.ui.seller.to.SellPaymentTo;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edcracken
 */
public final class SellerPaymentView extends Window {

    private final DecimalNumberField cash;
    private final DecimalNumberField card;
    private final SellerLogic viewLogic;
    private BeanFieldGroup<SellPaymentTo> fieldGroup;

    public SellerPaymentView(SellPaymentTo paymentTo, SellerLogic viewLogic) {
        super("Forma de pago"); // Set window caption
        center();
        this.viewLogic = viewLogic;
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        content.setSpacing(true);

        HorizontalLayout lyCash = new HorizontalLayout();
        HorizontalLayout lyCard = new HorizontalLayout();
        HorizontalLayout lySubtotal = new HorizontalLayout();
        content.addComponent(lyCash);
        content.addComponent(lyCard);
        content.addComponent(lySubtotal);

        lyCash.addComponent(new Label("Efectivo:"));
        lyCash.setSpacing(true);
        lyCash.addComponent(cash = new DecimalNumberField());

        lyCard.addComponent(new Label("Tarjeta:"));
        lyCard.addComponent(card = new DecimalNumberField());
        lyCard.setSpacing(true);

        Label subtotal;
        lySubtotal.addComponent(new Label("Subtotal:"));
        lySubtotal.addComponent(subtotal
                = new Label(DataFormatHelper
                        .formatNumber(paymentTo.getSubtotal())));
        subtotal.setId("subtotal");
        lySubtotal.setSpacing(true);

        setContent(content);
        setClosable(false);
        setModal(true);

        HorizontalLayout lyButtons = new HorizontalLayout();
        Button ok = new Button("Aceptar");
        ok.addClickListener((ClickEvent event) -> {
            try {
                fieldGroup.commit();
                close();
                viewLogic.saveSell(fieldGroup.getItemDataSource().getBean());
            }
            catch (FieldGroup.CommitException ex) {
                Logger.getLogger(SellerPaymentView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        lyButtons.addComponent(ok);

        Button ko = new Button("Cancelar");
        ok.addClickListener((ClickEvent event) -> Window::close);
        lyButtons.addComponent(ko);
        lyButtons.setSpacing(true);
        content.addComponent(lyButtons);

        fieldGroup = new BeanFieldGroup<>(SellPaymentTo.class);
        fieldGroup.setItemDataSource(paymentTo);
        fieldGroup.bindMemberFields(this);

    }

}
