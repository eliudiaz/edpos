/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller.to;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author edcracken
 */
@Getter
@Setter
public class SellPaymentTo {

    private BigDecimal cash = BigDecimal.ZERO;
    private BigDecimal card = BigDecimal.ZERO;
    private BigDecimal subtotal;

    public SellPaymentTo(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

}
