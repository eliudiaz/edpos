/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.DefaultErrorHandler;
import static com.vaadin.server.DefaultErrorHandler.doDefault;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import ed.cracken.pos.exception.ValidationException;
import ed.cracken.pos.ui.components.DecimalNumberField;
import ed.cracken.pos.ui.helpers.DataFormatHelper;
import ed.cracken.pos.ui.seller.to.SellPaymentTo;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edcracken
 */
public final class SellerPaymentView extends Window {

    private final DecimalNumberField cash;
    private final DecimalNumberField card;
    private BeanFieldGroup<SellPaymentTo> fieldGroup;
    private Label totalPayment;

    public SellerPaymentView(SellPaymentTo paymentTo, SellerLogic sellerView) {
        super("Forma de pago"); // Set window caption
        center();
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        HorizontalLayout lyCash = new HorizontalLayout();
        HorizontalLayout lyCard = new HorizontalLayout();
        HorizontalLayout lySubtotal = new HorizontalLayout();
        HorizontalLayout lyPayment = new HorizontalLayout();

        layout.addComponents(lyCash, lyCard, lyPayment, lySubtotal);

        lyCash.addComponent(new Label("Efectivo:"));
        lyCash.setSpacing(true);
        lyCash.addComponent(cash = new DecimalNumberField());

        lyCard.addComponent(new Label("Tarjeta:"));
        lyCard.addComponent(card = new DecimalNumberField());
        lyCard.setSpacing(true);
        FieldEvents.TextChangeListener changeListener;
        card.addTextChangeListener(changeListener = (FieldEvents.TextChangeEvent event) -> {
            if (!event.getText().isEmpty()) {
                try {
                    Double v = DataFormatHelper
                            .getFormatter()
                            .parse(event.getText())
                            .doubleValue() + DataFormatHelper
                                    .getFormatter()
                                    .parse(card.getValue())
                                    .doubleValue();
                    totalPayment.setValue(DataFormatHelper
                            .formatNumber(BigDecimal.valueOf(v)));
                    if (paymentTo.getSubtotal().doubleValue() < v) {
                        throw new ValidationException("Pago debe ser igual o menor al total!");
                    }
                }
                catch (ParseException ex) {
                    throw new ValidationException("Ingreso invalido!");
                }
            }
        });
        cash.addTextChangeListener(changeListener);

        lyPayment.addComponent(new Label("Total Pagado:"));
        lyPayment.addComponent(totalPayment
                = new Label(DataFormatHelper
                        .formatNumber(BigDecimal.ZERO)));
        totalPayment.addStyleName("subtotal");

        Label subtotal;
        lySubtotal.addComponent(new Label("Total Factura:"));
        lySubtotal.addComponent(subtotal
                = new Label(DataFormatHelper
                        .formatNumber(paymentTo.getSubtotal())));
        subtotal.addStyleName("subtotal");

        setContent(layout);
        setClosable(false);
        setModal(true);

        HorizontalLayout lyButtons = new HorizontalLayout();
        Button ok = new Button("Aceptar");
        ok.addClickListener((ClickEvent event) -> {
            try {
                fieldGroup.commit();
                checkTotalPayment();
                SellerPaymentView.this.close();
                sellerView.save(fieldGroup.getItemDataSource().getBean());
            }
            catch (FieldGroup.CommitException ex) {
                Logger.getLogger(SellerPaymentView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        lyButtons.addComponent(ok);

        Button ko = new Button("Cancelar");
        ok.addClickListener((ClickEvent event) -> {
            SellerPaymentView.this.close();
        });
        lyButtons.addComponent(ko);
        lyButtons.setSpacing(true);
        layout.addComponent(lyButtons);

        fieldGroup = new BeanFieldGroup<>(SellPaymentTo.class);
        fieldGroup.setItemDataSource(paymentTo);
        fieldGroup.bindMemberFields(this);

        UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                String cause = "<b>Causa de error:</b><br/>";
                for (Throwable t = event.getThrowable(); t != null;
                        t = t.getCause()) {
                    if (t.getCause() == null) // We're at final cause
                    {
                        cause += t.getMessage() + "<br/>";
                    }
                }

                layout.addComponent(new Label(cause, ContentMode.HTML));
                doDefault(event);
            }
        });

    }

    public void checkTotalPayment() {
        try {
            fieldGroup.commit();
            SellPaymentTo payment = fieldGroup.getItemDataSource().getBean();
            if (payment
                    .getSubtotal().doubleValue()
                    != payment.getCard().add(payment.getCash())
                            .doubleValue()) {
                throw new ValidationException("Pago debe ser igual al total!");
            }

        }
        catch (FieldGroup.CommitException ex) {
            Logger.getLogger(SellerPaymentView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
