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
public class PurchaseItemFormBase {

    private final TextField id;
    private final TextField description;
    private final TextField price;
    private final TextField quantity;
    private final TextField subtotal;

}
