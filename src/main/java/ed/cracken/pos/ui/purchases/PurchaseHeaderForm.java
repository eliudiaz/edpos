/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.purchases;

import com.vaadin.ui.TextField;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author edcracken
 */
@Getter
@AllArgsConstructor
public class PurchaseHeaderForm {

    private final TextField providerCode;
    private final TextField name;
    private final TextField documentNumber;
    private final TextField documentLetter;
    private final TextField documentDate;
}
